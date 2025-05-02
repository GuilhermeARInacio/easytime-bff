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

        if (senhaVazia) {
            throw new IllegalArgumentException("A senha não pode ser vazia");
        } else if (tamanhoInvalido) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres");
        } else if (!temLetras) {
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra");
        } else if (!temNumeros) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um número");
        } else if (!temCaracteresEspeciais) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um caractere especial");
        }
    }
}
