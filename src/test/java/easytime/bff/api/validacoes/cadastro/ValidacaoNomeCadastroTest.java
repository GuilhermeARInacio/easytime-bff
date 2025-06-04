package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidacaoNomeCadastroTest {

    private final ValidacaoNomeCadastro validator = new ValidacaoNomeCadastro();

    @Test
    @DisplayName("Deve validar nome com sucesso")
    void deveValidarEmailComSucesso() {
        String nomeValido = "nome";
        assertDoesNotThrow(() -> validator.validar(new UsuarioDto(nomeValido, "email", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com nome vazio")
    void deveDarErroComNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> validator.validar(new UsuarioDto("", "email", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com nome com menos de 3 caracteres")
    void deveDarErroComSenhaSemNumero() {
        String nomeInvalido = "n";
        assertThrows(IllegalArgumentException.class, () -> validator.validar(new UsuarioDto(nomeInvalido, "email", "login", "senha@123", "sector", "job", "role", true)));
    }

    @Test
    @DisplayName("Deve dar erro com nome com numeros")
    void deveDarErroComNomeComNumeros() {
        String nomeInvalido = "nome123";
        assertThrows(IllegalArgumentException.class, () -> validator.validar(new UsuarioDto(nomeInvalido, "email", "login", "senha@123", "sector", "job", "role", true)));
    }
}