package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoSenhaCadastroTest {

    private final ValidacaoSenhaCadastro validacaoSenhaCadastro = new ValidacaoSenhaCadastro();
    @Test
    @DisplayName("Deve validar senha com sucesso")
    void deveValidarSenhaComSucesso() {
        String senhaValida = "Senha123!";
        assertDoesNotThrow(() -> validacaoSenhaCadastro.validar(new UsuarioDto("nome", "email", "login", senhaValida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha vazia")
    void deveDarErroComSenhaVazia() {
        String senhaValida = "";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaCadastro.validar(new UsuarioDto("nome", "email", "login", "", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem número")
    void deveDarErroComSenhaSemNumero() {
        String senhaInvalida = "senhatop!";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaCadastro.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem letra")
    void deveDarErroComSenhaSemLetra() {
        String senhaInvalida = "1234567!";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaCadastro.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem caractere especial")
    void deveDarErroComSenhaSemCaractereEspecial() {
        String senhaInvalida = "senhatop";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaCadastro.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha menor que 8 caracteres")
    void deveDarErroComSenhaMenosDe8Caracteres() {
        String senhaInvalida = "senhato";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaCadastro.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Should throw error when password has no special character")
    void shouldThrowErrorWhenPasswordHasNoSpecialCharacter() {
        // Valid: length >= 8, has letters, has numbers, but no special character
        String senhaInvalida = "Senha1234";
        UsuarioDto usuario = new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validacaoSenhaCadastro.validar(usuario));
        assertEquals("A senha deve conter pelo menos um caractere especial", ex.getMessage());
    }

}