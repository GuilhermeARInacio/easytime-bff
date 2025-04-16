package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.DadosAutenticacao;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoSenha implements ValidacoesLogin {
    public void validar(DadosAutenticacao dto) {
        Boolean senhaVazia = dto.senha().isEmpty() || dto.senha().isBlank();
        Boolean tamanhoInvalido = dto.senha().length() < 8;
        Boolean temLetras = dto.senha().matches(".*[a-zA-Z].*");
        Boolean temNumeros = dto.senha().matches(".*\\d.*");
        Boolean temCaracteresEspeciais = dto.senha().matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        if(senhaVazia || tamanhoInvalido || !temLetras || !temNumeros || !temCaracteresEspeciais) {
            throw new RuntimeException("Formato de senha inválido");
        }
    }
}
