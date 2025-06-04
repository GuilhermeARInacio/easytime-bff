package easytime.bff.api.service;

import easytime.bff.api.dto.token.TokenDto;
import easytime.bff.api.dto.usuario.DadosAutenticacao;

import easytime.bff.api.validacoes.login.ValidacaoUsuarioLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Service
public class AutenticacaoService {

    private ValidacaoUsuarioLogin validacoes;

    @Value("${SRV_URL}")
    private String urlSrv;

    public TokenDto autenticar(DadosAutenticacao usuario) throws HttpMessageNotReadableException {
        RestTemplate restTemplate = new RestTemplate();
        String url = urlSrv + "login";

        return restTemplate.postForObject(url, usuario, TokenDto.class);
    }
}
