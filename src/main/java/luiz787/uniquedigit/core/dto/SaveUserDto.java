package luiz787.uniquedigit.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class SaveUserDto {
  @ApiModelProperty("User name")
  @NotBlank(message = "Name can't be empty or null.")
  String name;

  @ApiModelProperty("User email")
  @NotBlank(message = "Email can't be empty or null.")
  String email;

  @ApiModelProperty("Public encryption key")
  @NotBlank(message = "Public key can't be empty or null.")
  String publicKey;
}
