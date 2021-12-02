package calculator.core.user;

import calculator.core.uniquedigit.UniqueDigitCalculation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(value = "User id", required = false, example = "2")
  private Long id;

  @ApiModelProperty(value = "User name", example = "José")
  @Column(length = 1024)
  private String name;

  @ApiModelProperty(value = "User email", example = "example@example.com")
  @Column(length = 1024)
  private String email;

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
  @Column(length = 1024)
  private String publicKey;

  @ApiModelProperty(value = "List of calculated unique digits")
  @OneToMany
  private List<UniqueDigitCalculation> uniqueDigitsCalculated;

  public User() {
    uniqueDigitsCalculated = new ArrayList<>();
  }

  public void addUniqueDigit(final UniqueDigitCalculation uniqueDigit) {
    this.uniqueDigitsCalculated.add(uniqueDigit);
  }

  public static User fromSaveUserDto(final SaveUserDto saveUserDto) {
    final var user = new User();
    user.setName(saveUserDto.getName());
    user.setEmail(saveUserDto.getEmail());
    user.setUniqueDigitsCalculated(Collections.emptyList());
    user.setPublicKey(saveUserDto.getPublicKey());
    return user;
  }

  public static User fromSaveUserDtoAndId(final SaveUserDto saveUserDto, final Long id) {
    final var user = User.fromSaveUserDto(saveUserDto);
    user.setId(id);
    return user;
  }
}
