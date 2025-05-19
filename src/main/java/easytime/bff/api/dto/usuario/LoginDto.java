package easytime.bff.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDto (@NotNull @NotBlank String login){
}
