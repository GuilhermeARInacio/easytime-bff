package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.usuario.DadosAutenticacao;
import org.junit.jupiter.api.DisplayName;
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
class ValidacaoSenhaTest {
    @Test
    @DisplayName("Deve validar senha com sucesso")
    void deveValidarSenhaComSucesso() {
        ValidacaoSenhaLogin validacao = new ValidacaoSenhaLogin();
        String senhaValida = "Senha123!";
        assertDoesNotThrow(() -> validacao.validar(new DadosAutenticacao("usuario", senhaValida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha vazia")
    void deveDarErroComSenhaVazia() {
        ValidacaoSenhaLogin validacao = new ValidacaoSenhaLogin();
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new DadosAutenticacao("usuario", "")));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem número")
    void deveDarErroComSenhaSemNumero() {
        ValidacaoSenhaLogin validacao = new ValidacaoSenhaLogin();
        String senhaInvalida = "senhatop!";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem letra")
    void deveDarErroComSenhaSemLetra() {
        ValidacaoSenhaLogin validacao = new ValidacaoSenhaLogin();
        String senhaInvalida = "1234567!";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem caractere especial")
    void deveDarErroComSenhaSemCaractereEspecial() {
        ValidacaoSenhaLogin validacao = new ValidacaoSenhaLogin();
        String senhaInvalida = "senhatop";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

    @Test
    @DisplayName("Deve dar erro com senha menor que 8 caracteres")
    void deveDarErroComSenhaMenosDe8Caracteres() {
        ValidacaoSenhaLogin validacao = new ValidacaoSenhaLogin();
        String senhaInvalida = "senhato";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new DadosAutenticacao("usuario", senhaInvalida)));
    }

}