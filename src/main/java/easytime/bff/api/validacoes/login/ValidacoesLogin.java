package easytime.bff.api.validacoes.login;

import easytime.bff.api.dto.DadosAutenticacao;

public interface ValidacoesLogin {
    void validar(DadosAutenticacao dto);
}
