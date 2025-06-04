package easytime.bff.api.dto.pontos;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PedidoPonto(
        Integer id,
        String login,
        Integer idPonto,
        String status,
        String tipo_pedido,
        String gestorLogin,
        String dataAprovacao
){
}