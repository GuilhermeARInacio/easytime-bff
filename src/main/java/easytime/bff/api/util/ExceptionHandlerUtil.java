package easytime.bff.api.util;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class ExceptionHandlerUtil {

    public static ResponseEntity<?> tratarExcecao(Exception e, Logger logger) {
        if (e instanceof HttpClientErrorException) {
            return tratarHttpClientErrorException((HttpClientErrorException) e, logger);
        } else if (e instanceof IllegalArgumentException || e instanceof NullPointerException || e instanceof HttpMessageNotReadableException) {
            logger.warn("Erro de validação: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : "Erro de validação nos campos fornecidos.");
        } else if (e instanceof RuntimeException) {
            int statusCode = 400;
            if(e.getMessage().contains("404") && e.getMessage().contains("Usuário")) {
                statusCode = 404;
                return ResponseEntity.status(statusCode).body("Usuário não encontrado.");
            } else if (e.getMessage().contains("502") || e.getMessage().contains("500")) {
                return ResponseEntity.internalServerError().body(e.getMessage());
            } else if (e.getMessage().contains("Connection refused")) {
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
            logger.warn("Erro de execução: {}", e.getMessage());
            return ResponseEntity.status(statusCode).body(e.getMessage());
        } else {
            logger.error("Erro interno do servidor", e);
            return ResponseEntity.internalServerError().body("Erro interno do servidor.");
        }
    }

    private static ResponseEntity<?> tratarHttpClientErrorException(HttpClientErrorException e, Logger logger) {
        var statusCode = e.getStatusCode();
        var mensagem401 = e.getMessage().contains("código") ? "Tente enviar um novo código, ocorreu um erro pois o código está inválido." : "Usuário não autorizado.";
        logger.warn("Erro HTTP: {} - {}", statusCode.value(), e.getMessage());

        if (statusCode.is4xxClientError()) {
            return switch (statusCode.value()) {
                case 401 -> ResponseEntity.status(statusCode).body(mensagem401);
                case 404 -> ResponseEntity.status(statusCode).body("Serviço não encontrado.");
                default -> ResponseEntity.status(statusCode).body(e.getResponseBodyAsString());
            };
        } else if (statusCode.is5xxServerError()) {
            return ResponseEntity.status(statusCode).body("Erro interno do servidor.");
        }

        return ResponseEntity.status(statusCode).body(e.getMessage());
    }
}