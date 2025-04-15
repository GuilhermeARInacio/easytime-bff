package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.DadosAutenticacao;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuario implements ValidacoesLogin {
    public void validar(DadosAutenticacao dto) {
        if(dto.usuario().isEmpty() || dto.usuario().isBlank()) {
            throw new RuntimeException("O usuario não pode ser vazio");
        }
        if(!dto.usuario().matches(".*[a-zA-Z].*")) {
            throw new RuntimeException("Formato do usuario inválido");
        }
    }
}
