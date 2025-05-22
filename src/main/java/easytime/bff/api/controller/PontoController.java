package easytime.bff.api.controller;

import easytime.bff.api.dto.pontos.ConsultaPontoDTO;
import easytime.bff.api.dto.usuario.LoginDto;
import easytime.bff.api.infra.exception.InvalidUserException;
import easytime.bff.api.service.PontoService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RestController
@RequestMapping("/ponto")
public class PontoController {

    @Autowired
    private PontoService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(PontoController.class);

    @PostMapping
    @Operation(summary = "Registrar ponto", description = "Registra o ponto do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Batimento de ponto registrado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> registrarPonto(@Valid @RequestBody LoginDto login, HttpServletRequest request) {
        LOGGER.debug("Registrando ponto para o usuário: {}", login.login());
        try {
            ResponseEntity<?> response = service.registrarPonto(login, request);
            LOGGER.info("Ponto registrado para o usuário: {}", login.login());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            LOGGER.error("Erro ao registrar ponto para o usuário: {}", login.login(), e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ponto", description = "Deleta o ponto de acordo com o id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exclusão do ponto realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ponto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> excluirPonto(@PathVariable Integer id, HttpServletRequest request) {
        LOGGER.debug("Deletando resgistro de ponto com o id: {}", id);
        try {
            ResponseEntity<?> response = service.deletarPonto(id, request);
            LOGGER.info("Exclusão bem sucedida do registro com id: {}", id);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            LOGGER.error("Ponto não encontrado com o id: {}", id, e);
            return ResponseEntity.status(404).body("Ponto não encontrado.");
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar ponto com o id: {}", id, e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @PutMapping("/consulta")
    @Operation(summary = "Listar registro de pontos", description = "Lista os pontos registrados de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pontos registrados"),
            @ApiResponse(responseCode = "404", description = "Sem registro de pontos para o periodo informado"),
            @ApiResponse(responseCode = "400", description = "Erro ao consultar pontos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado ou usuario não encontrado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> consultaPonto(@Valid @RequestBody ConsultaPontoDTO dto, HttpServletRequest request) {
        LOGGER.debug("Consultando pontos do usuário: {}", dto.login());
        try {
            var response = service.consultarPonto(dto, request);
            LOGGER.debug("Consulta de pontos bem sucedida do usuário: {}", dto.login());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException.NotFound e){
            LOGGER.error("Nenhum ponto encontrado", e);
            return ResponseEntity.status(404).body("Nenhum ponto encontrado.");
        } catch (HttpClientErrorException.Unauthorized e){
            LOGGER.error("Usuário não encontrado", e);
            return ResponseEntity.status(401).body("Usuário não encontrado.");
        } catch (Exception e) {
            LOGGER.error("Erro ao consultar ponto", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

}
