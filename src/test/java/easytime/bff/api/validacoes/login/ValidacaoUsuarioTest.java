package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.usuario.DadosAutenticacao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoUsuarioTest {

    private final ValidacaoUsuarioLogin validacaoUsuarioLogin = new ValidacaoUsuarioLogin();

    @Test
    void deveValidarUsuarioComSucesso() {
        String usuarioValido = "usuario123";
        assertDoesNotThrow(() -> validacaoUsuarioLogin.validar(new DadosAutenticacao(usuarioValido, "senhaValida")));
    }

    @Test
    void deveDarErroComUsuarioVazio() {
        String usuarioInvalido = "";
        assertThrows(RuntimeException.class, () -> validacaoUsuarioLogin.validar(new DadosAutenticacao(usuarioInvalido, "senhaValida")));
    }

    @Test
    void deveDarErroComUsuarioSemLetras() {
        String usuarioInvalido = "1234567";
        assertThrows(IllegalArgumentException.class, () -> validacaoUsuarioLogin.validar(new DadosAutenticacao(usuarioInvalido, "senhaValida")));
    }

}