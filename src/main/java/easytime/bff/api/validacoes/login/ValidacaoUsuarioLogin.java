package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.DadosAutenticacao;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuarioLogin implements ValidacoesLogin {
    public void validar(DadosAutenticacao dto) {
        if(dto.login().isEmpty() || dto.login().isBlank()) {
            throw new RuntimeException("O usuário não pode ser vazio");
        }
        if(!dto.login().matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Formato do usuário inválido");
        }
    }
}
