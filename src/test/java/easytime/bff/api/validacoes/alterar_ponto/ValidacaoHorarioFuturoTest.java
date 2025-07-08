package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidacaoHorarioFuturoTest {

    private final ValidacaoHorarioFuturo validacao = new ValidacaoHorarioFuturo();

    @Test
    void validar_devePermitirHorarioAnteriorAoAtual() {
        LocalDateTime horarioAtual = LocalDateTime.of(2025, 07, 01, 19, 0);

        var dto = mock(AlterarPontoDto.class);
        when(dto.horarioAtual()).thenReturn(horarioAtual);
        when(dto.data()).thenReturn("10/06/2025");
        when(dto.entrada1()).thenReturn(LocalTime.of(8, 0));
        when(dto.saida1()).thenReturn(LocalTime.of(12, 0));
        when(dto.entrada2()).thenReturn(LocalTime.of(13, 0));
        when(dto.saida2()).thenReturn(LocalTime.of(17, 0));
        when(dto.entrada3()).thenReturn(null);
        when(dto.saida3()).thenReturn(null);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void validar_deveLancarExcecaoParaHorarioFuturo() {
        LocalDateTime horarioAtual = LocalDateTime.of(2025, 07, 01, 14, 0);

        var dto = mock(AlterarPontoDto.class);
        when(dto.horarioAtual()).thenReturn(horarioAtual);
        when(dto.data()).thenReturn("01/07/2025");
        when(dto.entrada1()).thenReturn(LocalTime.of(8, 0));
        when(dto.saida1()).thenReturn(LocalTime.of(12, 0));
        when(dto.entrada2()).thenReturn(LocalTime.of(13, 0));
        when(dto.saida2()).thenReturn(LocalTime.of(17, 0));
        when(dto.entrada3()).thenReturn(null);
        when(dto.saida3()).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> validacao.validar(dto));
    }
}