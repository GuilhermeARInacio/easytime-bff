package easytime.bff.api.validacoes.cadastro;

import easytime.bff.api.dto.usuario.UsuarioDto;

public interface ValidacoesCadastro {
    void validar(UsuarioDto dto);
}
