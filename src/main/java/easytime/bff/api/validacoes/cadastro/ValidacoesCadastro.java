package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.UsuarioDto;

public interface ValidacoesCadastro {
    void validar(UsuarioDto dto);
}
