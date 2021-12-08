package calculator.core.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CreateUserDto {
    @ApiModelProperty(value = "User id", example = "2")
    @NotNull(message = "Id can't be null.")
    Long id;
    @ApiModelProperty(value = "User name", example = "José")
    @NotBlank(message = "Name can't be empty or null.")
    String name;

    @ApiModelProperty(value = "User email", example = "example@example.com")
    @NotBlank(message = "Email can't be empty or null.")
    String email;

    @ApiModelProperty(
            value = "Public encryption key",
            example =
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAskZ+N9l2eV2z8aQ"
                            + "vpi+3v0REQ1IpnyqQ3pBUl3Do2xQjs6XD7D3W5Tdjp0h4h7ISxgZz0xg5r51G"
                            + "RQ5jynLUDGRnFnmvpgdUqVCJNP3vWobCPMrbo0tWk+Giw3ssJ90uXzteU9vpt"
                            + "MkagB/FOZYX80e0hDK8eNUUoq4xM0H6LJHJbV8y0Ukcs/7JSwZkoO0huBVJZW"
                            + "PV/52WmU0ftv0U5U1DY8WUSPrDvSGt9fRAGiTfuqNvEYw1Pkx++WUwq0XDE/6"
                            + "zPJ/jhtm6yuGzcnKhEjtzbLTfexR8zcQbg5IYexWcl8CpC2vWCc2M0TdKbMN1"
                            + "o8J2ECxK6bKpcul4NDInBwIDAQAB")
    @NotBlank(message = "Public key can't be empty or null.")
    String publicKey;
}
