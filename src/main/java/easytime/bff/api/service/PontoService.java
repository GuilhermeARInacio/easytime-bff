package easytime.bff.api.service;

import easytime.bff.api.dto.pontos.*;
import easytime.bff.api.infra.security.TokenService;
import easytime.bff.api.util.HttpHeaderUtil;
import easytime.bff.api.validacoes.alterar_ponto.ValidacaoAlterarPonto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
public class PontoService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private TokenService tokenService;

    @Value("${SRV_URL}")
    private String urlSrv;

    private String urlPonto = "ponto/";

    @Autowired
    private List<ValidacaoAlterarPonto> validacoes;

    public ResponseEntity<Object> registrarPonto(BaterPonto dto, HttpServletRequest request) {
        String url = urlSrv + "ponto";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        HttpEntity<BaterPonto> entity = new HttpEntity<>(dto, headers);
        return restTemplate.exchange(url, POST, entity, Object.class);
    }

    public ResponseEntity<String> deletarPonto(Integer id, HttpServletRequest request) {
        String url = urlSrv + urlPonto + id;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, DELETE, new HttpEntity<>(headers), String.class);
    }

    public ResponseEntity<List<RegistroCompletoDto>> consultarPonto(ConsultaPontoDTO dto, HttpServletRequest request) {
        String url = urlSrv + urlPonto + "consulta";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);
        HttpEntity<ConsultaPontoDTO> entity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(url, PUT, entity,
                new ParameterizedTypeReference<List<RegistroCompletoDto>>() {});
    }

    public ResponseEntity<String> alterarPonto(AlterarPontoDto dto, HttpServletRequest request) {
        String url = urlSrv + urlPonto + "alterar";

        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);
        HttpEntity<AlterarPontoDto> entity = new HttpEntity<>(dto, headers);

        validacoes.forEach(validacao -> validacao.validar(dto));

        return restTemplate.exchange(url, PUT, entity, String.class);
    }

    public ResponseEntity<List<RegistroCompletoDto>> listarPontos(HttpServletRequest request) {
        String url = urlSrv + urlPonto;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, GET, new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<RegistroCompletoDto>>() {});
    }

    public ResponseEntity<List<PedidoPonto>> listarPedidos(HttpServletRequest request) {
        String url = urlSrv + urlPonto +"pedidos/all" ;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, GET, new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<PedidoPonto>>() {});
    }

    public ResponseEntity<String> aprovarPonto(Integer id, HttpServletRequest request) {
        String url = urlSrv + urlPonto + "aprovar/" + id;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, POST, new HttpEntity<>(headers), String.class);
    }

    public ResponseEntity<String> reprovarPonto(Integer id, HttpServletRequest request) {
        String url = urlSrv + urlPonto + "reprovar/" + id;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, POST, new HttpEntity<>(headers), String.class);
    }

    public ResponseEntity<AlterarPontoDto> consultarPedidoId(Integer id, HttpServletRequest request) {
        String url = urlSrv + urlPonto + "pedido/" + id;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, GET, new HttpEntity<>(headers), AlterarPontoDto.class);
    }

    public ResponseEntity<List<PedidoPonto>> filtrarPedidos(FiltroPedidos dto, HttpServletRequest request) {
        String url = urlSrv + urlPonto + "pedidos/filtrar";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);
        HttpEntity<FiltroPedidos> entity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(url, PUT, entity,
                new ParameterizedTypeReference<List<PedidoPonto>>() {});
    }
}
