package easytime.bff.api.dto.pontos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record AlterarPontoDto(
        String login,
        Integer idPonto,
        String data,
        LocalTime entrada1,
        LocalTime saida1,
        LocalTime entrada2,
        LocalTime saida2,
        LocalTime entrada3,
        LocalTime saida3,
        String justificativa,
        String status,
        LocalTime horarioAtual
) {
}
