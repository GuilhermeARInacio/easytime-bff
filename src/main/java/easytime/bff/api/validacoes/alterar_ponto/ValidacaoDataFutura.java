package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import easytime.bff.api.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ValidacaoDataFutura implements ValidacaoAlterarPonto{
    public void validar(AlterarPontoDto dto) {
        var date = DateUtil.convertUserDateToDBDate(dto.data());
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data não pode ser futura.");
        }
    }
}
