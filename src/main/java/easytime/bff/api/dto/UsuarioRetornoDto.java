package easytime.bff.api.dto;

public record UsuarioRetornoDto (
        Integer id,
        String nome,
        String email,
        String login,
        String sector,
        String jobTitle,
        String role,
        boolean isActive
){
}
