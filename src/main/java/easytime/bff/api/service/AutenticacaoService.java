package easytime.bff.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.validacoes.login.ValidacaoUsuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Service
public class AutenticacaoService {

    private ValidacaoUsuario validacoes;

    public String autenticar(DadosAutenticacao usuario) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://70b9bf47-dcb1-46e9-9886-1110d671967d-00-1upha6j38mgjy.riker.replit.dev:8080/login";
            String response = restTemplate.postForObject(url, usuario, String.class);

            return response;
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }
}
