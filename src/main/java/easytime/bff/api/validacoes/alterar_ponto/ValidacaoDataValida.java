package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ValidacaoDataValida implements ValidacaoAlterarPonto{
    @Override
    public void validar(AlterarPontoDto dto) {
        if (dto.data() == null || dto.data().trim().isEmpty()){
            throw new IllegalArgumentException("deu erro validacaodatavidali");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(dto.data(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("validacaodatavidali");
        }
    }
}
