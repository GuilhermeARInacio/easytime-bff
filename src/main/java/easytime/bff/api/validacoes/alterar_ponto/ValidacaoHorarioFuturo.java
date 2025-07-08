package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoHorarioFuturo implements ValidacaoAlterarPonto{
    public void validar(AlterarPontoDto dto) {
        if(
                dto.entrada1().isAfter(dto.horarioAtual()) ||
                dto.saida1().isAfter(dto.horarioAtual()) ||
                dto.entrada2().isAfter(dto.horarioAtual()) ||
                dto.saida2().isAfter(dto.horarioAtual()) ||
                dto.entrada3().isAfter(dto.horarioAtual()) ||
                dto.saida3().isAfter(dto.horarioAtual())
        ) {
            throw new IllegalArgumentException("Os horários não podem ser após o horário atual");
        }
    }
}
