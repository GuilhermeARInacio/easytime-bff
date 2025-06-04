package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidacaoEmailCadastroTest {

    private final ValidacaoEmailCadastro validacaoEmailCadastro = new ValidacaoEmailCadastro();
    @Test
    @DisplayName("Deve validar email com sucesso")
    void deveValidarEmailComSucesso() {
        String emailValido = "email@gmail.com";
        assertDoesNotThrow(() -> validacaoEmailCadastro.validar(new UsuarioDto("nome", emailValido, "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com email vazio")
    void deveDarErroComEmailVazio() {
        assertThrows(IllegalArgumentException.class, () -> validacaoEmailCadastro.validar(new UsuarioDto("nome", "", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com email sem @")
    void deveDarErroComEmailSemArroba() {
        String emailInvalido = "email.com";
        assertThrows(IllegalArgumentException.class, () -> validacaoEmailCadastro.validar(new UsuarioDto("nome", emailInvalido, "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro de email sem final valido")
    void deveDarErroComFinalInvalido() {
        String emailInvalido = "email@gmail";
        assertThrows(IllegalArgumentException.class, () -> validacaoEmailCadastro.validar(new UsuarioDto("nome", emailInvalido, "login", "senha@123", "sector", "job", "role", true)));
    }
}