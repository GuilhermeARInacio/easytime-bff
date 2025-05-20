package easytime.bff.api.dto.pontos;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;

public record RegistroCompletoDto (
        Integer id,
        LocalDate data,
        Duration horasTrabalhadas,
        Time entrada1,
        Time saida1,
        Time entrada2,
        Time saida2,
        Time entrada3,
        Time saida3,
        Status status
){


}
