package luiz787.uniquedigit.core;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import luiz787.uniquedigit.core.dto.SaveUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
@Api("User related operations.")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;
  private final CryptoUtils crypto;

  @GetMapping("/{id}")
  @ApiOperation("Find an user by id.")
  @ApiResponses({
    @ApiResponse(
        code = 200,
        message = "The user associated with the id provided.",
        response = User.class),
    @ApiResponse(code = 404, message = "User not found.")
  })
  public ResponseEntity<User> findById(@PathVariable @Valid @ApiParam("User id") final long id) {
    return userRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @ApiOperation("Create a new user.")
  @ApiResponses({@ApiResponse(code = 201, message = "The created user.", response = User.class)})
  public ResponseEntity<User> create(@RequestBody @Valid final SaveUserDto userToCreate) {
    final User encryptedUser = crypto.encryptUserData(User.fromSaveUserDto(userToCreate));
    final User createdUser = userRepository.save(encryptedUser);

    final URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdUser.getId())
            .toUri();

    return ResponseEntity.created(location).body(createdUser);
  }

  @PutMapping("/{id}")
  @ApiOperation("Update an existing user.")
  @ApiResponses({
    @ApiResponse(code = 200, message = "The updated user.", response = User.class),
    @ApiResponse(code = 404, message = "User not found.")
  })
  public ResponseEntity<User> update(
      @PathVariable final long id, @RequestBody @Valid final SaveUserDto newUserData) {
    return userRepository
        .findById(id)
        .map((existingUser) -> crypto.encryptUserData(User.fromSaveUserDtoAndId(newUserData, id)))
        // .map(crypto::encryptUserData)
        .map((userToSave) -> ResponseEntity.ok(userRepository.save(userToSave)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @ApiOperation("Delete an user by id.")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Deleted user successfully."),
    @ApiResponse(code = 404, message = "User not found.")
  })
  public ResponseEntity<Void> delete(@PathVariable @Valid final long id) {
    var userOptional = userRepository.findById(id);

    if (userOptional.isPresent()) {
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
