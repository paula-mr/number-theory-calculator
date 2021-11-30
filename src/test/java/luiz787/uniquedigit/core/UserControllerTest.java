package luiz787.uniquedigit.core;

import luiz787.uniquedigit.core.dto.SaveUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {

  private static final long ID = 42;

  @Mock private UserRepository userRepository;
  private UserController userController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    userController = new UserController(userRepository);
  }

  @Test
  void findById_UserNotFound_ShouldReturnHttpStatus404() {
    when(userRepository.findById(ID)).thenReturn(Optional.empty());

    final var response = userController.findById(ID);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void findById_UserNotFound_ResponseBodyShouldBeNull() {
    when(userRepository.findById(ID)).thenReturn(Optional.empty());

    final var response = userController.findById(ID);
    assertNull(response.getBody());
  }

  @Test
  void findById_UserFound_ShouldReturnHttpStatus200() {
    when(userRepository.findById(ID)).thenReturn(Optional.of(new User()));

    final var response = userController.findById(ID);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void findById_UserFound_ResponseBodyShouldNotBeNull() {
    when(userRepository.findById(ID)).thenReturn(Optional.of(new User()));

    final var response = userController.findById(ID);

    assertNotNull(response.getBody());
  }

  @Test
  void create_Success_ShouldReturnHttpStatus201() {
    final User expectedUser = new User();
    expectedUser.setEmail("test@email.com");
    expectedUser.setName("Teste");

    when(userRepository.save(expectedUser)).thenReturn(expectedUser);

    final var response = userController.create(new SaveUserDto("Teste", "test@email.com"));

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void update_UserExists_ShouldReturnHttpStatus200() {
    final User expectedUser = new User();
    expectedUser.setEmail("test@email.com");
    expectedUser.setName("Teste");

    when(userRepository.findById(ID)).thenReturn(Optional.of(new User()));
    when(userRepository.save(expectedUser)).thenReturn(expectedUser);

    final var response = userController.update(ID, new SaveUserDto("Teste", "test@email.com"));

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void update_UserDoesNotExist_ShouldReturnHttpStatus404() {
    when(userRepository.findById(ID)).thenReturn(Optional.empty());

    final var response = userController.update(ID, new SaveUserDto("Teste", "test@email.com"));

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void update_UserDoesNotExist_UserRepositorySaveShouldNeverBeCalled() {
    when(userRepository.findById(ID)).thenReturn(Optional.empty());

    userController.update(ID, new SaveUserDto("Teste", "test@email.com"));

    verify(userRepository, never()).save(any());
  }

  @Test
  void delete_UserExists_ShouldReturnEmptyBody() {
    when(userRepository.findById(ID)).thenReturn(Optional.of(new User()));
    final var response = userController.delete(ID);

    assertNull(response.getBody());
  }

  @Test
  void delete_UserExists_HttpStatusShouldBe204() {
    when(userRepository.findById(ID)).thenReturn(Optional.of(new User()));
    final var response = userController.delete(ID);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  @Test
  void delete_UserExists_ShouldCallUserRepositoryDeleteByIdWithProvidedId() {
    when(userRepository.findById(ID)).thenReturn(Optional.of(new User()));
    userController.delete(ID);

    verify(userRepository).deleteById(ID);
  }

  @Test
  void delete_UserDoesNotExist_ResponseBodyShouldBeNull() {
    when(userRepository.findById(ID)).thenReturn(Optional.empty());
    final var response = userController.delete(ID);

    assertNull(response.getBody());
  }

  @Test
  void delete_UserDoesNotExist_HttpStatusShouldBe404() {
    when(userRepository.findById(ID)).thenReturn(Optional.empty());
    final var response = userController.delete(ID);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
