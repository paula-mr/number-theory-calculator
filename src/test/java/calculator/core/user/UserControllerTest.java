package calculator.core.user;

import calculator.core.CryptoUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Tag("IntegrationTest")
class UserControllerTest {

  private static final long ID = 1;
  private static final String PUBLIC_KEY = "MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQBR6JO2ON2nP9M24JJ0jU/r"
          + "Wp2eGjaV3yUBOSPokZQeU3ldApIPEtLL7arXKSW1Cg0u+wMo2Xq/YSdQcSmhCZA4"
          + "SVawXSXQPwaB3Uz6iNjTcjqXGuXzxTZtANCzPCELKNPT8mqUa5zwusZzq9COvFPN"
          + "SrvuLrlcyYw6DJMg6Ht0dAUfdfrITg7aIGSdNw4Zorv4J30hOo0HHhFgl07EpwSR"
          + "SHbnTenHhClgFXMdIrxHgDcyRCVR0mf7BP6F9rGDmWc2F+t257/G8CZkMWpcprAG"
          + "gjLiDFFggrxdKgYexeHkX5Hm4OpjHQVjGFO8ZxNxCYbL7u0B4IfvX58OLhhjqx+F"
          + "AgMBAAE=";

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CryptoUtils cryptoUtils;
  private UserController userController;
  @Autowired
  private DataSource dataSource;

  @BeforeEach
  public void setup() {
    userController = new UserController(userRepository, cryptoUtils);
  }

  @AfterEach
  public void resetDb() {
    userRepository.deleteAll();
  }

  @Test
  void findById_UserNotFound_ShouldReturnHttpStatus404() {
    final var response = userController.findById(ID);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void findById_UserNotFound_ResponseBodyShouldBeNull() {
    final var response = userController.findById(ID);
    assertNull(response.getBody());
  }

  @Test
  void findById_UserFound_ShouldReturnHttpStatus200() {
    final var user = new User();
    user.setId(ID);
    userRepository.save(user);

    final var response = userController.findById(ID);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void findById_UserFound_ResponseBodyShouldNotBeNull() {
    final var user = new User();
    user.setId(ID);
    userRepository.save(user);

    final var response = userController.findById(ID);

    assertNotNull(response.getBody());
  }

  @Test
  void create_Success_ShouldReturnHttpStatus201() {
    final var response =
        userController.create(new CreateUserDto(ID, "Teste", "test@email.com", PUBLIC_KEY));

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void update_UserExists_ShouldReturnHttpStatus200() {
    final User expectedUser = new User();
    expectedUser.setId(ID);
    expectedUser.setEmail("test@email.com");
    expectedUser.setName("Teste");
    expectedUser.setPublicKey("dummy key");
    expectedUser.setUniqueDigitsCalculated(Collections.emptyList());

    userRepository.save(expectedUser);

    final var response =
        userController.update(ID, new SaveUserDto("Teste", "test@email.com", PUBLIC_KEY));

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void update_UserDoesNotExist_ShouldReturnHttpStatus404() {
    final var response =
        userController.update(ID, new SaveUserDto("Teste", "test@email.com", "dummy key"));

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void delete_UserExists_ShouldReturnEmptyBody() {
    final var user = new User();
    user.setId(ID);
    userRepository.save(user);

    final var response = userController.delete(ID);

    assertNull(response.getBody());
  }

  @Test
  void delete_UserExists_HttpStatusShouldBe204() {
    final var user = new User();
    user.setId(ID);
    userRepository.save(user);

    final var response = userController.delete(ID);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void delete_UserDoesNotExist_ResponseBodyShouldBeNull() {
    final var response = userController.delete(ID);

    assertNull(response.getBody());
  }

  @Test
  void delete_UserDoesNotExist_HttpStatusShouldBe404() {
    final var response = userController.delete(ID);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
