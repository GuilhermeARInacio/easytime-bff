package easytime.bff.api.controller;

import easytime.bff.api.dto.senha.CodigoValidacao;
import easytime.bff.api.dto.senha.EmailRequest;
import easytime.bff.api.service.SenhaService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class SenhaController {

    @Autowired
    private SenhaService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(SenhaController.class);

    @PostMapping("/senha/redefinir")
    @Operation(summary = "Redefinir senha", description = "Redefine a senha do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato dos campos inválido ou campo vazio"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> redefinirSenha(@RequestBody CodigoValidacao codigo, HttpServletRequest request) {
        LOGGER.debug("Redefinindo senha para o usuário: {}", codigo.email());
        try{
            if(codigo == null || codigo.email() == null || codigo.senha() == null || codigo.code().isBlank() || codigo.code() == null) {
                throw new IllegalArgumentException("Preencha todos os campos.");
            }

            ResponseEntity<String> response = service.redefinirSenha(codigo, request);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e){
            LOGGER.error("Erro ao redefinir senha para o usuário: {}", codigo.email(), e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @PostMapping("senha/enviar-codigo")
    @Operation(summary = "Enviar código de validação", description = "Envia um código de validação para o email do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Código enviado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato dos campos inválido ou campo vazio"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> enviarCodigo(@RequestBody EmailRequest email, HttpServletRequest request) {
        LOGGER.debug("Enviando código para o usuário: {}", email.email());
        try{
            if(email == null || email.email() == null) {
                throw new IllegalArgumentException("Preencha todos os campos.");
            }

            ResponseEntity<String> response = service.enviarCodigo(email, request);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e){
            LOGGER.error("Erro ao enviar código para o usuário: {}", email.email(), e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }
}
