package luiz787.uniquedigit.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CryptoUtilsTest {

  private CryptoUtils crypto;

  @BeforeEach
  void setup() {
    crypto = new CryptoUtils();
  }

  @Test
  void encryptUserData_ShouldWorkCorrectly() {
    final User user = new User();
    user.setName("Test");
    user.setEmail("mock@test.com");
    user.setPublicKey(
        "MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQBR6JO2ON2nP9M24JJ0jU/r"
            + "Wp2eGjaV3yUBOSPokZQeU3ldApIPEtLL7arXKSW1Cg0u+wMo2Xq/YSdQcSmhCZA4"
            + "SVawXSXQPwaB3Uz6iNjTcjqXGuXzxTZtANCzPCELKNPT8mqUa5zwusZzq9COvFPN"
            + "SrvuLrlcyYw6DJMg6Ht0dAUfdfrITg7aIGSdNw4Zorv4J30hOo0HHhFgl07EpwSR"
            + "SHbnTenHhClgFXMdIrxHgDcyRCVR0mf7BP6F9rGDmWc2F+t257/G8CZkMWpcprAG"
            + "gjLiDFFggrxdKgYexeHkX5Hm4OpjHQVjGFO8ZxNxCYbL7u0B4IfvX58OLhhjqx+F"
            + "AgMBAAE=");

    final User userEncrypted = crypto.encryptUserData(user);

    assertNotEquals(user.getEmail(), userEncrypted.getEmail());
    assertNotEquals(user.getName(), userEncrypted.getName());
    assertEquals(user.getPublicKey(), userEncrypted.getPublicKey());
    assertEquals(user.getId(), userEncrypted.getId());
    assertEquals(user.getUniqueDigitsCalculated(), userEncrypted.getUniqueDigitsCalculated());
  }
}
