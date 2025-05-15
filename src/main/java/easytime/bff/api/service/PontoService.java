package easytime.bff.api.service;

import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.dto.pontos.TimeLogDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

@Service
public class PontoService {

    private RestTemplate restTemplate;

    @Value("${url.srv}")
    private String urlSrv;

    public ResponseEntity<?> registrarPonto(LoginDto login, HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }

            String url = urlSrv + "ponto";
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            HttpEntity<LoginDto> entity = new HttpEntity<>(login, headers);
            return restTemplate.exchange(url, POST, entity, TimeLogDto.class);
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<?> deletarPonto(Integer id, HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }

            String url = urlSrv + "ponto/" + id;
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            return restTemplate.exchange(url, DELETE, new HttpEntity<>(headers), String.class);
        } catch (Exception e) {
            throw e;
        }
    }
}
