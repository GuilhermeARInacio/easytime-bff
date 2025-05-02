package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoEmailCadastro implements ValidacoesCadastro {
    public void validar(UsuarioDto dto) {
        Boolean emailVazio = dto.email().isEmpty() || dto.email().isBlank();
        Boolean temLetras = dto.email().matches(".*[a-zA-Z].*");
        Boolean temArroba = dto.email().contains("@");
        Boolean validarFinal = dto.email().contains(".com") || dto.email().contains(".br");

        if (emailVazio){
            throw new IllegalArgumentException("O email não pode ser vazio");
        } else if(!temLetras || !temArroba || !validarFinal) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }
}
