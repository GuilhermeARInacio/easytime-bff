package easytime.bff.api.dto.pontos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Time;
import java.time.LocalDate;

public record TimeLogDto(
        @Schema(description = "Login do usuário", example = "mkenzo")
        String login,
        @Schema(description = "Data do ponto", example = "2023-10-01")
        LocalDate data,
        @Schema(description = "Horário do ponto", example = "08:00:00")
        Time horarioBatida,
        @Schema(description = "Status do ponto", example = "PENDENTE")
        Status status
) {
}
