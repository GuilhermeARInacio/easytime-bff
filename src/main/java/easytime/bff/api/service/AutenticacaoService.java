package easytime.bff.api.service;

import easytime.bff.api.dto.DadosAutenticacao;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Service
public class AutenticacaoService {

    public String autenticar(DadosAutenticacao usuario) throws HttpMessageNotReadableException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            RestTemplate restTemplate = new RestTemplate();
            // String url = "https://70b9bf47-dcb1-46e9-9886-1110d671967d-00-1upha6j38mgjy.riker.replit.dev:8080/login";
            String url = "http://localhost:8080/login";
            String response = restTemplate.postForObject(url, usuario, String.class);

            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        } catch (HttpMessageNotReadableException e){
            throw new HttpMessageNotReadableException("Formato de JSON Inválido. Verifique o corpo da requisição");
        }
    }
}
