package luiz787.uniquedigit.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import luiz787.uniquedigit.core.dto.SaveUserDto;

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
  @ApiModelProperty("User id")
  private Long id;

  @ApiModelProperty("User name")
  @Column(length = 1024)
  private String name;

  @ApiModelProperty("User email")
  @Column(length = 1024)
  private String email;

  @ApiModelProperty("Public encryption key.")
  @Column(length = 1024)
  private String publicKey;

  @OneToMany private List<DigitalRootCalculation> uniqueDigitsCalculated;

  public User() {
    uniqueDigitsCalculated = new ArrayList<>();
  }

  public void addUniqueDigit(final DigitalRootCalculation uniqueDigit) {
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
