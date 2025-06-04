package easytime.bff.api.dto.pontos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaPontoDTO(
        @NotBlank @NotNull String dtInicio,
        @NotBlank @NotNull String dtFinal
) {
}
