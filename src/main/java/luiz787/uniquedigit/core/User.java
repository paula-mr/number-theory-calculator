package luiz787.uniquedigit.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import luiz787.uniquedigit.core.dto.SaveUserDto;

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
    @ApiModelProperty(value = "User id")
    private Long id;
    @ApiModelProperty(value = "User name")
    private String name;
    @ApiModelProperty(value = "User email")
    private String email;
    @OneToMany
    private List<DigitalRootCalculation> uniqueDigitsCalculated;

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
        return user;
    }

    public static User fromSaveUserDtoAndId(final SaveUserDto saveUserDto, final Long id) {
        final var user = User.fromSaveUserDto(saveUserDto);
        user.setId(id);
        return user;
    }
}
