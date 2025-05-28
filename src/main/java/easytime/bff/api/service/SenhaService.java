package easytime.bff.api.service;

import easytime.bff.api.dto.senha.CodigoValidacao;
import easytime.bff.api.dto.senha.EmailRequest;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

@Service
public class SenhaService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${SRV_URL}")
    private String urlSrv;

    public ResponseEntity<String> redefinirSenha(CodigoValidacao codigo) {
        String url = urlSrv + "redefine-senha";

        HttpEntity<CodigoValidacao> entity = new HttpEntity<>(codigo);
        return restTemplate.exchange(url, POST, entity, String.class);
    }

    public ResponseEntity<String> enviarCodigo(EmailRequest email) {
        String url = urlSrv + "send-email";

        HttpEntity<EmailRequest> entity = new HttpEntity<>(email);
        return restTemplate.exchange(url, POST, entity, String.class);
    }
}
