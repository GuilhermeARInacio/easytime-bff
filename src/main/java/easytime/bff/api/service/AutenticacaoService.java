package easytime.bff.api.service;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.validacoes.login.ValidacaoUsuario;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private ValidacaoUsuario validacoes;

    public String autenticar(DadosAutenticacao usuario) {
        //chamar o srv
        // retornar o token?
        return "token";
    }
}
