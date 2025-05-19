package easytime.bff.api.service;

import easytime.bff.api.dto.pontos.ConsultaPontoDTO;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.*;

@Service
public class PontoService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${url.srv}")
    private String urlSrv;

    public ResponseEntity<Object> registrarPonto(LoginDto login, HttpServletRequest request) {

        String url = urlSrv + "ponto";
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        HttpEntity<LoginDto> entity = new HttpEntity<>(login, headers);
        return restTemplate.exchange(url, POST, entity, Object.class);
    }

    public ResponseEntity<Object> deletarPonto(Integer id, HttpServletRequest request) {

        String url = urlSrv + "ponto/" + id;
        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        return restTemplate.exchange(url, DELETE, new HttpEntity<>(headers), Object.class);
    }

    public ResponseEntity<Object> consultarPonto(ConsultaPontoDTO dto, HttpServletRequest request) {

        String url = urlSrv + "ponto/consulta";

        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        HttpEntity<ConsultaPontoDTO> entity = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(url, GET, new HttpEntity<>(headers), Object.class);
    }
}
