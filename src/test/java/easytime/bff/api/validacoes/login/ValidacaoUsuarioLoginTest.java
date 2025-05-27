package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.usuario.DadosAutenticacao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "SRV_URL=http://localhost:8080"
})
class ValidacaoUsuarioLoginTest {

    @Test
    void deveValidarUsuarioComSucesso() {
        ValidacaoUsuarioLogin validacao = new ValidacaoUsuarioLogin();
        String usuarioValido = "usuario123";
        assertDoesNotThrow(() -> validacao.validar(new DadosAutenticacao(usuarioValido, "senhaValida")));
    }

    @Test
    void deveDarErroComUsuarioVazio() {
        ValidacaoUsuarioLogin validacao = new ValidacaoUsuarioLogin();
        String usuarioInvalido = "";
        assertThrows(RuntimeException.class, () -> validacao.validar(new DadosAutenticacao(usuarioInvalido, "senhaValida")));
    }

    @Test
    void deveDarErroComUsuarioSemLetras() {
        ValidacaoUsuarioLogin validacao = new ValidacaoUsuarioLogin();
        String usuarioInvalido = "1234567";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new DadosAutenticacao(usuarioInvalido, "senhaValida")));
    }

}