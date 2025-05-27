package easytime.bff.api.dto.senha;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CodigoValidacao(
        @NotBlank @NotNull String code,
        @NotBlank @NotNull String email,
        @NotBlank @NotNull String senha
) {
}
