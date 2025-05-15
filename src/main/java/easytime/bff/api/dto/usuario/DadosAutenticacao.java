package easytime.bff.api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para autenticação do usuário")
public record DadosAutenticacao (
        @Schema(description = "Login do usuário", example = "usuario123")
        String login,
        @Schema(description = "Senha do usuário", example = "senha#123")
        String senha
) {
}
