package luiz787.uniquedigit.core.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

@Value
public class SaveUserDto {
    @ApiModelProperty("User name")
    String name;
    @ApiModelProperty("User email")
    String email;
    @ApiModelProperty("Public encryption key")
    String publicKey;
}
