package easytime.bff.api.service;

import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.util.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.Enumeration;

@Service
public class UsuarioService {

    public ResponseEntity<Object> criarUsuario(UsuarioDto dto, HttpServletRequest request) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/users/create";
            HttpHeaders headers = new HttpHeaders();

            headers.set("Content-Type", "application/json");
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.add(headerName, request.getHeader(headerName));
            }

            if (headers.containsKey("Content-Length")) {
                headers.remove("Content-Length");
            }

            HttpEntity<UsuarioDto> entity = new HttpEntity<>(dto, headers);
            return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage(), e);
        }
    }
}
