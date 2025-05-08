package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.UsuarioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ValidacaoSenhaCadastroTest {
    @Test
    @DisplayName("Deve validar senha com sucesso")
    void deveValidarSenhaComSucesso() {
        ValidacaoSenhaCadastro validacao = new ValidacaoSenhaCadastro();
        String senhaValida = "Senha123!";
        assertDoesNotThrow(() -> validacao.validar(new UsuarioDto("nome", "email", "login", senhaValida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha vazia")
    void deveDarErroComSenhaVazia() {
        ValidacaoSenhaCadastro validacao = new ValidacaoSenhaCadastro();
        String senhaValida = "";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "email", "login", "", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem número")
    void deveDarErroComSenhaSemNumero() {
        ValidacaoSenhaCadastro validacao = new ValidacaoSenhaCadastro();
        String senhaInvalida = "senhatop!";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem letra")
    void deveDarErroComSenhaSemLetra() {
        ValidacaoSenhaCadastro validacao = new ValidacaoSenhaCadastro();
        String senhaInvalida = "1234567!";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha sem caractere especial")
    void deveDarErroComSenhaSemCaractereEspecial() {
        ValidacaoSenhaCadastro validacao = new ValidacaoSenhaCadastro();
        String senhaInvalida = "senhatop";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com senha menor que 8 caracteres")
    void deveDarErroComSenhaMenosDe8Caracteres() {
        ValidacaoSenhaCadastro validacao = new ValidacaoSenhaCadastro();
        String senhaInvalida = "senhato";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("nome", "email", "login", senhaInvalida, "sector", "job", "role", true)));
    }

}