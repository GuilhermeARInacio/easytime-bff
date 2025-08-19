package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNomeCadastro implements ValidacoesCadastro {
    public void validar(UsuarioDto dto) {
        Boolean temLetras = dto.nome().matches("^[A-Za-zÀ-ÿ\\s]+$");
        Boolean nomeVazio = dto.nome().isEmpty() || dto.nome().isBlank();
        Boolean nomeTamanho = dto.nome().length() < 3;

        if (nomeVazio) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        } else if (nomeTamanho) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres");
        } else if (!temLetras) {
            throw new IllegalArgumentException("O nome não pode conter números ou caracteres especiais");
        }
    }
}
