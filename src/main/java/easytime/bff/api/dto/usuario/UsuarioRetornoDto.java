package easytime.bff.api.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do usuário retornados pela API")
public record UsuarioRetornoDto (
        @Schema(description = "ID do usuário", example = "1")
        Integer id,
        @Schema(description = "Nome do usuário", example = "User")
        String nome,
        @Schema(description = "E-mail do usuário", example = "user@email.com")
        String email,
        @Schema(description = "Login do usuário", example = "user123")
        String login,
        @Schema(description = "Setor do usuário", example = "TI")
        String sector,
        @Schema(description = "Cargo do usuário", example = "Desenvolvedor")
        String jobTitle,
        @Schema(description = "Função do usuário", example = "ROLE_USER")
        String role,
        @Schema(description = "Ativo", example = "true")
        boolean isActive
){
}
