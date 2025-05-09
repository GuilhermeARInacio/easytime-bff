package easytime.bff.api.service;

import easytime.bff.api.dto.DadosAutenticacao;

import easytime.bff.api.validacoes.login.ValidacaoUsuarioLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Service
public class AutenticacaoService {

    private ValidacaoUsuarioLogin validacoes;

    @Value("${URL_SRV}")
    private String urlSrv;

    public String autenticar(DadosAutenticacao usuario) throws HttpMessageNotReadableException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            RestTemplate restTemplate = new RestTemplate();
            String url = urlSrv + "login";
            String response = restTemplate.postForObject(url, usuario, String.class);

            return response;
        } catch (Exception e) {
            throw e;
        }
    }
}
