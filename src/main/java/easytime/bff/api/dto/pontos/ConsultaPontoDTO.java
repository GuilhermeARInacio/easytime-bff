package easytime.bff.api.dto.pontos;

import jakarta.validation.constraints.NotBlank;

public record ConsultaPontoDTO(
        String login,
        String dtInicio,
        String dtFinal
) {
}
