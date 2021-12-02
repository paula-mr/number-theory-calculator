package luiz787.uniquedigit.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
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
    byte[] rawBytes = Base64.getDecoder().decode(base64);
    var keySpec = new X509EncodedKeySpec(rawBytes);
    try {
      var keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePublic(keySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      log.error("Failed to create key", e);
      throw new RuntimeException(e);
    }
  }

  private String encrypt(String data, PublicKey key) {
    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      final var encryptedByteArray = Base64.getEncoder().encode(cipher.doFinal(data.getBytes()));
      return new String(encryptedByteArray, StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error("Failed to encrypt data", e);
      throw new RuntimeException(e);
    }
  }

  public User encryptUserData(final User user) {
      var providedKeyString = user.getPublicKey();
      PublicKey key = publicKeyFromBase64String(providedKeyString);
      final var newUser = new User();
      newUser.setEmail(encrypt(user.getEmail(), key));
      newUser.setName(encrypt(user.getName(), key));
      newUser.setPublicKey(user.getPublicKey());
      newUser.setUniqueDigitsCalculated(user.getUniqueDigitsCalculated());
      newUser.setId(user.getId());
      return newUser;
  }
}
