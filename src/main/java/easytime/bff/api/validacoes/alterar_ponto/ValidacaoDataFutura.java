package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidacaoDataFutura implements ValidacaoAlterarPonto{
    public void validar(AlterarPontoDto dto) {
        if (LocalDate.parse(dto.data()).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data não pode ser futura.");
        }
    }
}
