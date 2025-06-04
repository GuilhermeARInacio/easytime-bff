package easytime.bff.api.dto.pontos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDate;

public record TimeLogDto(
        @Schema(description = "Login do usuário", example = "mkenzo")
        @NotBlank @NotNull String login,
        @Schema(description = "Data do ponto", example = "01/10/2023")
        @NotBlank @NotNull String data,
        @Schema(description = "Horário do ponto", example = "08:00:00")
        @NotBlank @NotNull Time horarioBatida,
        @Schema(description = "Status do ponto", example = "PENDENTE")
        @NotBlank @NotNull Status status
) {
}
