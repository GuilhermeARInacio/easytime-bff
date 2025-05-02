package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoSenhaCadastro implements ValidacoesCadastro {
    public void validar(UsuarioDto dto) {
        Boolean senhaVazia = dto.password().isEmpty() || dto.password().isBlank();
        Boolean tamanhoInvalido = dto.password().length() < 8;
        Boolean temLetras = dto.password().matches(".*[a-zA-Z].*");
        Boolean temNumeros = dto.password().matches(".*\\d.*");
        Boolean temCaracteresEspeciais = dto.password().matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        if(senhaVazia || tamanhoInvalido || !temLetras || !temNumeros || !temCaracteresEspeciais) {
            throw new IllegalArgumentException("Formato de senha inválido");
        }
    }
}
