package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuario implements ValidacoesCadastro {
    public void validar(UsuarioDto dto) {
        if(dto.login().isEmpty() || dto.login().isBlank()) {
            throw new RuntimeException("O usuário não pode ser vazio");
        }
        if(!dto.login().matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Formato do usuário inválido");
        }
    }
}
