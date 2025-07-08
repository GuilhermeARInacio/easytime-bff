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
                "login",
                123,
                "2024-06-10",
                LocalTime.of(8, 0),
                LocalTime.of(12, 0),
                LocalTime.of(13, 0),
                LocalTime.of(17, 0),
                null,
                null,
                "teste",
                "status",
                LocalTime.now()
        );
        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void validar_deveLancarExcecaoParaHorarioAntesDas6() {
        AlterarPontoDto dto = new AlterarPontoDto(
                "login",
                123,
                "2024-06-10",
                LocalTime.of(5, 59),
                null,
                null,
                null,
                null,
                null,
                "teste",
                "status",
                LocalTime.now()
        );
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validacao.validar(dto));
        assertEquals("Horários entre 23h e 6h não são permitidos.", ex.getMessage());
    }

    @Test
    void validar_deveLancarExcecaoParaHorarioDepoisDas23() {
        AlterarPontoDto dto = new AlterarPontoDto(
                "login",
                123,
                "2024-06-10",
                LocalTime.of(23, 1),
                null,
                null,
                null,
                null,
                null,
                "teste",
                "status",
                LocalTime.now()
        );
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validacao.validar(dto));
        assertEquals("Horários entre 23h e 6h não são permitidos.", ex.getMessage());
    }

}