package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.usuario.DadosAutenticacao;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuarioLogin implements ValidacoesLogin {
    public void validar(DadosAutenticacao dto) {
        if(dto.login().isEmpty() || dto.login().isBlank()) {
            throw new RuntimeException("O usuário não pode ser vazio");
        }
    }
}
