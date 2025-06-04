package easytime.bff.api.validacoes.alterar_ponto;

import easytime.bff.api.dto.pontos.AlterarPontoDto;

public interface ValidacaoAlterarPonto {
    void validar(AlterarPontoDto dto);
}
