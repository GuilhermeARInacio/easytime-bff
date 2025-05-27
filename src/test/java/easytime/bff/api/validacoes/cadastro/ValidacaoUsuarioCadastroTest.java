package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "SRV_URL=http://localhost:8080"
})
class ValidacaoUsuarioCadastroTest {

    @Test
    void deveValidarUsuarioComSucesso() {
        ValidacaoUsuarioCadastro validacao = new ValidacaoUsuarioCadastro();
        String usuarioValido = "usuario123";
        assertDoesNotThrow(() -> validacao.validar(new UsuarioDto("nome", "email", usuarioValido, "senhaValida", "setor", "cargo", "role", true)));
    }

    @Test
    void deveDarErroComUsuarioVazio() {
        ValidacaoUsuarioCadastro validacao = new ValidacaoUsuarioCadastro();
        assertThrows(RuntimeException.class, () -> validacao.validar(new UsuarioDto("nome", "email", "", "senhaValida", "setor", "cargo", "role", true)));
    }

    @Test
    void deveDarErroComUsuarioSemLetras() {
        ValidacaoUsuarioCadastro validacao = new ValidacaoUsuarioCadastro();
        String usuarioInvalido = "1234567";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "email", usuarioInvalido, "senhaValida", "setor", "cargo", "role", true)));
    }

}