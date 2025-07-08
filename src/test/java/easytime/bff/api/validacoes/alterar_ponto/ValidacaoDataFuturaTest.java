package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidacaoDataFuturaTest {

    private final ValidacaoHorarioFuturo validacao = new ValidacaoHorarioFuturo();

    @Test
    void validar_devePermitirDataHojeOuPassada() {

        // Data de hoje
        LocalDate dateHoje = LocalDate.now();
        String dataConvertida = dateHoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Data passada
        var datePassada = LocalDate.now().minusDays(1);
        var c = datePassada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        var dtoHoje = mock(AlterarPontoDto.class);
        when(dtoHoje.data()).thenReturn(dataConvertida);

        var dtoPassada = mock(AlterarPontoDto.class);
        when(dtoPassada.data()).thenReturn(c);

        // Asserts
        assertDoesNotThrow(() -> validacao.validar(dtoHoje));
        assertDoesNotThrow(() -> validacao.validar(dtoPassada));
    }

    @Test
    void validar_deveLancarExcecaoParaDataFutura() {
        AlterarPontoDto dtoFutura = mock(AlterarPontoDto.class);
        when(dtoFutura.data()).thenReturn(LocalDate.now().plusDays(1).toString());

        assertThrows(IllegalArgumentException.class,
                () -> validacao.validar(dtoFutura));
    }
}