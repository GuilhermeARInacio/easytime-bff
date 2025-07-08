package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import easytime.bff.api.util.DateUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class ValidacaoHorarioFuturo implements ValidacaoAlterarPonto{
    public void validar(AlterarPontoDto dto) {
        List<String> campos = Arrays.asList("entrada1", "saida1", "entrada2", "saida2", "entrada3", "saida3");

        for(String campo : campos) {
            try{
                Method metodo = AlterarPontoDto.class.getDeclaredMethod(campo);
                LocalTime time = (LocalTime) metodo.invoke(dto);
                if(time != null) {
                    LocalDateTime timeAndDay = time.atDate(DateUtil.convertUserDateToDBDate(dto.data()));
                    if (timeAndDay.isAfter(dto.horarioAtual())){
                        throw new IllegalArgumentException("O horário não pode ser após o horário atual.");
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Erro ao validar campo: " + campo, e);
            }
        }

//        if(
//                dto.entrada1().isAfter(dto.horarioAtual()) ||
//                dto.saida1().isAfter(dto.horarioAtual()) ||
//                dto.entrada2().isAfter(dto.horarioAtual()) ||
//                dto.saida2().isAfter(dto.horarioAtual()) ||
//                dto.entrada3().isAfter(dto.horarioAtual()) ||
//                dto.saida3().isAfter(dto.horarioAtual())
//        ) {
//            throw new IllegalArgumentException("Os horários não podem ser após o horário atual");
//        }
    }
}
