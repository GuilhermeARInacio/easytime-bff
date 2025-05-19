package easytime.bff.api.dto.pontos;

import jakarta.validation.constraints.NotBlank;

public record ConsultaPontoDTO(
        @NotBlank String login,
        @NotBlank String dtInicio,
        @NotBlank String dtFinal
) {
}
