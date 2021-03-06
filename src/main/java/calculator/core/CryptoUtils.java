package calculator.core;

import lombok.extern.log4j.Log4j2;
import calculator.core.user.User;
import calculator.exception.CryptoException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Log4j2
public class CryptoUtils {

  private PublicKey publicKeyFromBase64String(final String base64) {
    final byte[] rawBytes = Base64.getDecoder().decode(base64);
    final var keySpec = new X509EncodedKeySpec(rawBytes);
    try {
      final var keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePublic(keySpec);
    } catch (final NoSuchAlgorithmException e) {
      log.error("Failed to create key", e);
      throw new RuntimeException(e);
    } catch (final InvalidKeySpecException e) {
      log.error("Failed to create key", e);
      throw new CryptoException(e);
    }
  }

  private String encrypt(final String data, final PublicKey key) {
    try {
      final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      final var encryptedByteArray = Base64.getEncoder().encode(cipher.doFinal(data.getBytes()));
      return new String(encryptedByteArray, StandardCharsets.UTF_8);
    } catch (final InvalidKeyException e) {
      throw new CryptoException(e);
    } catch (final Exception e) {
      log.error("Failed to encrypt data", e);
      throw new RuntimeException(e);
    }
  }

  public User encryptUserData(final User user) {
    final var providedKeyString = user.getPublicKey();
    final PublicKey key = publicKeyFromBase64String(providedKeyString);
    final var newUser = new User();
    newUser.setEmail(encrypt(user.getEmail(), key));
    newUser.setName(encrypt(user.getName(), key));
    newUser.setPublicKey(user.getPublicKey());
    newUser.setUniqueDigitsCalculated(user.getUniqueDigitsCalculated());
    newUser.setId(user.getId());
    return newUser;
  }
}
