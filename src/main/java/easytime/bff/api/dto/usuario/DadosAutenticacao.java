package easytime.bff.api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para autenticação do usuário")
public record DadosAutenticacao (
        @Schema(description = "Login do usuário", example = "usuario123")
        @NotNull @NotBlank String login,
        @Schema(description = "Senha do usuário", example = "senha#123")
        @NotNull @NotBlank String senha
) {
}
