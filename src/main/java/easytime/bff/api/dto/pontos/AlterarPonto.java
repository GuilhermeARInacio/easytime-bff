package easytime.bff.api.dto.pontos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record AlterarPonto(
        String userLogin,
        @NotBlank @NotNull String targetLogin,
        @NotNull Integer idPonto,
        @NotBlank @NotNull String data,
        LocalTime entrada1,
        LocalTime saida1,
        LocalTime entrada2,
        LocalTime saida2,
        LocalTime entrada3,
        LocalTime saida3
) {
    public AlterarPonto(String userLogin, AlterarPontoDto dto) {
        this(
                userLogin,
                dto.login(),
                dto.idPonto(),
                dto.data(),
                dto.entrada1(),
                dto.saida1(),
                dto.entrada2(),
                dto.saida2(),
                dto.entrada3(),
                dto.saida3()
        );
    }
}