package easytime.bff.api.dto.senha;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailRequest(
        @NotNull @NotBlank String email
) {
}
