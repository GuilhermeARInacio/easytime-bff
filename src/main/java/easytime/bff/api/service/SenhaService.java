package easytime.bff.api.service;

import easytime.bff.api.dto.CodigoValidacao;
import easytime.bff.api.dto.EmailRequest;
import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@Service
public class SenhaService {

    private RestTemplate restTemplate;

    public ResponseEntity<String> redefinirSenha(CodigoValidacao codigo, HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }
            String url = "http://localhost:8080/redefine-senha";
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            HttpEntity<CodigoValidacao> entity = new HttpEntity<>(codigo, headers);
            return restTemplate.exchange(url, POST, entity, String.class);
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> enviarCodigo(EmailRequest email, HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }
            String url = "http://localhost:8080/send-email";
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            HttpEntity<EmailRequest> entity = new HttpEntity<>(email, headers);
            return restTemplate.exchange(url, POST, entity, String.class);
        } catch (Exception e) {
            throw e;
        }
    }
}
