package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoUsuarioCadastro implements ValidacoesCadastro {
    public void validar(UsuarioDto dto) {
        if(dto.login().isEmpty() || dto.login().isBlank()) {
            throw new RuntimeException("O usuário não pode ser vazio");
        }
        if(!dto.login().matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Usuário deve conter letras");
        }
    }
}
