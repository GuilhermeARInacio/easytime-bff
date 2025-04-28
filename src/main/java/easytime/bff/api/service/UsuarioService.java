package easytime.bff.api.service;

import easytime.bff.api.dto.UsuarioDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import org.springframework.http.HttpHeaders;
import java.util.Enumeration;

@Service
public class UsuarioService {

    public ResponseEntity<?> criarUsuario(UsuarioDto dto, HttpServletRequest request) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            // String url = "https://70b9bf47-dcb1-46e9-9886-1110d671967d-00-1upha6j38mgjy.riker.replit.dev:8080/users/create";
            String url = "http://localhost:8080/users/create";
            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.add(headerName, request.getHeader(headerName));
            }

            HttpEntity<UsuarioDto> entity = new HttpEntity<>(dto, headers);

            ResponseEntity<?> response = restTemplate.postForObject(url, entity, ResponseEntity.class);

            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        } catch (HttpMessageNotReadableException e){
            throw new HttpMessageNotReadableException("Formato de JSON Inválido. Verifique o corpo da requisição");
        }
    }

}
