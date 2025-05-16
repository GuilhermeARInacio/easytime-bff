package easytime.bff.api.dto.senha;

public record CodigoValidacao(
        String code,
        String email,
        String senha
) {
}
