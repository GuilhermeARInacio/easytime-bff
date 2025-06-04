package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.usuario.DadosAutenticacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoSenhaTest {

    private final ValidacaoSenhaLogin validacaoSenhaLogin = new ValidacaoSenhaLogin();

    @Test
    @DisplayName("Deve validar senha com sucesso")
    void deveValidarSenhaComSucesso() {
        String senhaValida = "Senha123!";
        assertDoesNotThrow(() -> validacaoSenhaLogin.validar(new DadosAutenticacao("usuario", senhaValida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha vazia")
    void deveDarErroComSenhaVazia() {
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaLogin.validar(new DadosAutenticacao("usuario", "")));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem número")
    void deveDarErroComSenhaSemNumero() {
        String senhaInvalida = "senhatop!";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaLogin.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem letra")
    void deveDarErroComSenhaSemLetra() {
        String senhaInvalida = "1234567!";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaLogin.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem caractere especial")
    void deveDarErroComSenhaSemCaractereEspecial() {
        String senhaInvalida = "senhatop";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaLogin.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha menor que 8 caracteres")
    void deveDarErroComSenhaMenosDe8Caracteres() {
        String senhaInvalida = "senhato";
        assertThrows(IllegalArgumentException.class, () -> validacaoSenhaLogin.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

}