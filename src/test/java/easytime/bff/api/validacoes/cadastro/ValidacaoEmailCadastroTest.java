package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.UsuarioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class ValidacaoEmailCadastroTest {
    @Test
    @DisplayName("Deve validar email com sucesso")
    void deveValidarEmailComSucesso() {
        ValidacaoEmailCadastro validacao = new ValidacaoEmailCadastro();
        String emailValido = "email@gmail.com";
        assertDoesNotThrow(() -> validacao.validar(new UsuarioDto("nome", emailValido, "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com email vazio")
    void deveDarErroComEmailVazio() {
        ValidacaoEmailCadastro validacao = new ValidacaoEmailCadastro();
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com email sem @")
    void deveDarErroComEmailSemArroba() {
        ValidacaoEmailCadastro validacao = new ValidacaoEmailCadastro();
        String emailInvalido = "email.com";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", emailInvalido, "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro de email sem final valido")
    void deveDarErroComFinalInvalido() {
        ValidacaoEmailCadastro validacao = new ValidacaoEmailCadastro();
        String emailInvalido = "email@gmail";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", emailInvalido, "login", "senha@123", "sector", "job", "role", true)));
    }
}