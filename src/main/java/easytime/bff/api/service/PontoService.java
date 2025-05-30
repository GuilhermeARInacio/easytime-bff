package easytime.bff.api.service;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import easytime.bff.api.dto.pontos.ConsultaPontoDTO;
import easytime.bff.api.dto.pontos.RegistroCompletoDto;
import easytime.bff.api.dto.usuario.LoginDto;
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

    @Value("${SRV_URL}")
    private String urlSrv;

    @Autowired
    private List<ValidacaoAlterarPonto> validacoes;

    public ResponseEntity<Object> registrarPonto(LoginDto login, HttpServletRequest request) {
        String url = urlSrv + "ponto";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        HttpEntity<LoginDto> entity = new HttpEntity<>(login, headers);
        return restTemplate.exchange(url, POST, entity, Object.class);
    }

    public ResponseEntity<String> deletarPonto(Integer id, HttpServletRequest request) {
        String url = urlSrv + "ponto/" + id;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, DELETE, new HttpEntity<>(headers), String.class);
    }

    public ResponseEntity<List<RegistroCompletoDto>> consultarPonto(ConsultaPontoDTO dto, HttpServletRequest request) {
        String url = urlSrv + "ponto/consulta";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);
        HttpEntity<ConsultaPontoDTO> entity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(url, PUT, entity,
                new ParameterizedTypeReference<List<RegistroCompletoDto>>() {});
    }

    public ResponseEntity<RegistroCompletoDto> alterarPonto(AlterarPontoDto dto, HttpServletRequest request) {
        String url = urlSrv + "ponto/alterar";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);
        HttpEntity<AlterarPontoDto> entity = new HttpEntity<>(dto, headers);

        validacoes.forEach(validacao -> validacao.validar(dto));

        return restTemplate.exchange(url, PUT, entity, RegistroCompletoDto.class);
    }

    public ResponseEntity<List<RegistroCompletoDto>> listarPontos(HttpServletRequest request) {
        String url = urlSrv + "ponto";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, GET, new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<RegistroCompletoDto>>() {});
    }
}
