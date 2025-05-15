package easytime.bff.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record LoginDto (@NotBlank String login){
}
