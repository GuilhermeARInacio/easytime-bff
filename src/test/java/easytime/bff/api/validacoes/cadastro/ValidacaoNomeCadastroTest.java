package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.UsuarioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ValidacaoNomeCadastroTest {
    @Test
    @DisplayName("Deve validar nome com sucesso")
    void deveValidarEmailComSucesso() {
        ValidacaoNomeCadastro validacao = new ValidacaoNomeCadastro();
        String nomeValido = "nome";
        assertDoesNotThrow(() -> validacao.validar(new UsuarioDto(nomeValido, "email", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com nome vazio")
    void deveDarErroComNomeVazio() {
        ValidacaoNomeCadastro validacao = new ValidacaoNomeCadastro();
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto("", "email", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com nome com menos de 3 caracteres")
    void deveDarErroComSenhaSemNumero() {
        ValidacaoNomeCadastro validacao = new ValidacaoNomeCadastro();
        String nomeInvalido = "n";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto(nomeInvalido, "email", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com nome com numeros")
    void deveDarErroComNomeComNumeros() {
        ValidacaoNomeCadastro validacao = new ValidacaoNomeCadastro();
        String nomeInvalido = "nome123";
        assertThrows(IllegalArgumentException.class, () -> validacao.validar(new UsuarioDto(nomeInvalido, "email", "login", "senha@123", "sector", "job", "role", true)));
    }
}