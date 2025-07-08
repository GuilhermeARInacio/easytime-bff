package easytime.bff.api.dto.pontos;

public record FiltroPedidos(
    String dtInicio,
    String dtFinal,
    Status status,
    String tipo
) {
}
