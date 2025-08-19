package easytime.bff.api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados do usuário para cadastro")
public record UsuarioDto(
        @Schema(description = "ID do usuário", example = "user")
        @NotBlank String nome,
        @Schema(description = "E-mail do usuário", example = "user@email.com")
        @NotBlank String email,
        @Schema(description = "Login do usuário", example = "user123")
        @NotBlank String login,
        @Schema(description = "Senha do usuário", example = "senha#123")
        @NotBlank String password,
        @Schema(description = "Setor do usuário", example = "TI")
        @NotBlank String sector,
        @Schema(description = "Cargo do usuário", example = "Desenvolvedor")
        @NotBlank String jobTitle,
        @Schema(description = "Função do usuário", example = "ROLE_USER")
        @NotBlank String role,
        @Schema(description = "Ativo", example = "true")
        @NotBlank boolean isActive
) {
}
