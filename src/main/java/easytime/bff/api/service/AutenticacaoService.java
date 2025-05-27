package easytime.bff.api.service;

import easytime.bff.api.dto.usuario.DadosAutenticacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AutenticacaoService {

    @Value("${SRV_URL}")
    private String urlSrv;

    public String autenticar(DadosAutenticacao usuario){
        RestTemplate restTemplate = new RestTemplate();
        String url = urlSrv + "login";

        return restTemplate.postForObject(url, usuario, String.class);
    }
}
