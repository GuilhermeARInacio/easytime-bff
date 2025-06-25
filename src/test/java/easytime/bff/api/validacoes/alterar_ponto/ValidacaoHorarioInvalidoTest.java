package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ValidacaoHorarioInvalidoTest {

    private final ValidacaoHorarioInvalido validacao = new ValidacaoHorarioInvalido();

    @Test
    void validar_devePermitirHorariosValidosOuNulos() {
        AlterarPontoDto dto = new AlterarPontoDto(
                "login",              // login
                123,                       // idPonto
                "2024-06-10",              // data (must be yyyy-MM-dd)
                LocalTime.of(8, 0),        // entrada1
                LocalTime.of(12, 0),       // saida1
                LocalTime.of(13, 0),       // entrada2
                LocalTime.of(17, 0),       // saida2
                null,                      // entrada3 (can be null)
                null                       // saida3 (can be null)
        );
        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void validar_deveLancarExcecaoParaHorarioAntesDas6() {
        AlterarPontoDto dto = new AlterarPontoDto(
                "login",              // login
                123,                       // idPonto
                "2024-06-10",              // data (must be yyyy-MM-dd)
                LocalTime.of(5, 59),        // entrada1
                null,       // saida1
                null,       // entrada2
                null,       // saida2
                null,                      // entrada3 (can be null)
                null                       // saida3 (can be null)
        );
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validacao.validar(dto));
        assertEquals("Horários entre 23h e 6h não são permitidos.", ex.getMessage());
    }

    @Test
    void validar_deveLancarExcecaoParaHorarioDepoisDas23() {
        AlterarPontoDto dto = new AlterarPontoDto(
                "login",              // login
                123,                       // idPonto
                "2024-06-10",              // data (must be yyyy-MM-dd)
                LocalTime.of(23, 1),        // entrada1
                null,       // saida1
                null,       // entrada2
                null,       // saida2
                null,                      // entrada3 (can be null)
                null                       // saida3 (can be null)
        );
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validacao.validar(dto));
        assertEquals("Horários entre 23h e 6h não são permitidos.", ex.getMessage());
    }

}