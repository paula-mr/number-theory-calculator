package luiz787.uniquedigit.core;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import luiz787.uniquedigit.core.dto.SaveUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Api("User related operations.")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @GetMapping("/{id}")
  @ApiOperation("Find an user by id.")
  @ApiResponses({
    @ApiResponse(
        code = 200,
        message = "The user associated with the id provided.",
        response = User.class),
    @ApiResponse(code = 404, message = "User not found.")
  })
  public ResponseEntity<User> findById(@PathVariable @ApiParam("User id") final long id) {
    return userRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  @ApiOperation("Create a new user.")
  @ApiResponses({@ApiResponse(code = 201, message = "The created user.", response = User.class)})
  public ResponseEntity<User> create(@RequestBody final SaveUserDto userToCreate) {
    final User userCreated = userRepository.save(User.fromSaveUserDto(userToCreate));

    final URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userCreated.getId())
            .toUri();

    return ResponseEntity.created(location).body(userCreated);
  }

  @PutMapping("/{id}")
  @ApiOperation("Update an existing user.")
  @ApiResponses({
    @ApiResponse(code = 200, message = "The updated user.", response = User.class),
    @ApiResponse(code = 404, message = "User not found.")
  })
  public ResponseEntity<User> update(
      @PathVariable final long id, @RequestBody final SaveUserDto newUserData) {
    return userRepository
        .findById(id)
        .map((existingUser) -> User.fromSaveUserDtoAndId(newUserData, id))
        .map((userToSave) -> ResponseEntity.ok(userRepository.save(userToSave)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @ApiOperation("Delete an user by id.")
  @ApiResponses({
    @ApiResponse(code = 204, message = "Deleted user successfully."),
    @ApiResponse(code = 404, message = "User not found.")
  })
  public ResponseEntity<Void> delete(@PathVariable final long id) {
    var userOptional = userRepository.findById(id);

    if (userOptional.isPresent()) {
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
