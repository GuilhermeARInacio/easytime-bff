package easytime.bff.api.dto.pontos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PedidoPonto(
        Integer id,
        String login,
        Integer idPonto,
        String dataRegistro,
        String tipoPedido,
        String dataPedido,
        String statusRegistro,
        String statusPedido,
        @JsonProperty("alteracaoPonto")
        AlterarPonto alterarPonto
){
}