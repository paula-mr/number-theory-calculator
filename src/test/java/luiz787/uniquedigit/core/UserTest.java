package luiz787.uniquedigit.core;

import luiz787.uniquedigit.core.dto.SaveUserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void fromSaveUserDto_shouldSaveNameAndEmailProperly() {
        final SaveUserDto dto = new SaveUserDto("Teste", "teste@tempmail.com", "dummy key");

        final var user = User.fromSaveUserDto(dto);
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
    }

    @Test
    void fromSaveUserDtoAndId_ShouldSaveNameEmailAndIdProperly() {
        final SaveUserDto dto = new SaveUserDto("Teste", "teste@tempmail.com", "dummy key");
        final long id = 42;

        final var user = User.fromSaveUserDtoAndId(dto, id);
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(id, user.getId());
    }
}