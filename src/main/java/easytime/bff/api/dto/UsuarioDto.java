package easytime.bff.api.dto;

public record UsuarioDto(
        String nome,
        String email,
        String login,
        String password,
        String sector,
        String jobTitle,
        String role,
        boolean isActive,
        boolean valid
) {
}
