package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidacaoUsuarioCadastroTest {

    private final ValidacaoUsuarioCadastro validacaoUsuarioCadastro = new ValidacaoUsuarioCadastro();
    @Test
    void deveValidarUsuarioComSucesso() {
        String usuarioValido = "usuario123";
        assertDoesNotThrow(() -> validacaoUsuarioCadastro.validar(new UsuarioDto("nome", "email", usuarioValido, "senhaValida", "setor", "cargo", "role", true)));
    }

    @Test
    void deveDarErroComUsuarioVazio() {
        assertThrows(RuntimeException.class, () -> validacaoUsuarioCadastro.validar(new UsuarioDto("nome", "email", "", "senhaValida", "setor", "cargo", "role", true)));
    }

    @Test
    void deveDarErroComUsuarioSemLetras() {
        String usuarioInvalido = "1234567";
        assertThrows(IllegalArgumentException.class, () -> validacaoUsuarioCadastro.validar(new UsuarioDto("nome", "email", usuarioInvalido, "senhaValida", "setor", "cargo", "role", true)));
    }

}