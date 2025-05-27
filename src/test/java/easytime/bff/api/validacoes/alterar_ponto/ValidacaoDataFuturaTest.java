package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidacaoDataFuturaTest {

    private final ValidacaoDataFutura validacao = new ValidacaoDataFutura();

    @Test
    void validar_devePermitirDataHojeOuPassada() {
        AlterarPontoDto dtoHoje = mock(AlterarPontoDto.class);
        when(dtoHoje.data()).thenReturn(LocalDate.now().toString());

        AlterarPontoDto dtoPassada = mock(AlterarPontoDto.class);
        when(dtoPassada.data()).thenReturn(LocalDate.now().minusDays(1).toString());

        assertDoesNotThrow(() -> validacao.validar(dtoHoje));
        assertDoesNotThrow(() -> validacao.validar(dtoPassada));
    }

    @Test
    void validar_deveLancarExcecaoParaDataFutura() {
        AlterarPontoDto dtoFutura = mock(AlterarPontoDto.class);
        when(dtoFutura.data()).thenReturn(LocalDate.now().plusDays(1).toString());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validacao.validar(dtoFutura));
        assertEquals("Data não pode ser futura.", ex.getMessage());
    }
}