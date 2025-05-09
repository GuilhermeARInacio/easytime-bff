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
            if(e.getMessage().contains("404")){
                statusCode = 404;
                return ResponseEntity.status(statusCode).body("Usuário não encontrado.");
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
        var mensagem = e.getMessage();
        logger.warn("Erro HTTP: {} - {}", statusCode.value(), e.getMessage());

        if (statusCode.is4xxClientError()) {
            return switch (statusCode.value()) {
                case 401 -> ResponseEntity.status(statusCode).body("Usuário não autorizado.");
                case 404 -> ResponseEntity.status(statusCode).body(mensagem != null ? mensagem : "Serviço não encontrado.");
                default -> ResponseEntity.status(statusCode).body(e.getResponseBodyAsString());
            };
        } else if (statusCode.is5xxServerError()) {
            return ResponseEntity.status(statusCode).body("Erro interno do servidor.");
        }

        return ResponseEntity.status(statusCode).body(e.getMessage());
    }
}