package easytime.bff.api.dto.pontos;

import java.time.LocalTime;

public record AlterarPonto(
        String data,
        LocalTime entrada1,
        LocalTime saida1,
        LocalTime entrada2,
        LocalTime saida2,
        LocalTime entrada3,
        LocalTime saida3,
        String justificativa
) {
}
