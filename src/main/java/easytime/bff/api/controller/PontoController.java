package easytime.bff.api.controller;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
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

import java.time.DateTimeException;

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
    public ResponseEntity<?> consultaPonto(@RequestBody ConsultaPontoDTO dto, HttpServletRequest request) {
        LOGGER.debug("Consultando pontos do usuário: {}", dto.login());
        if(dto.dtInicio() == null || dto.dtFinal() == null || dto.login() == null || dto.login().isBlank() || dto.dtInicio().isBlank() || dto.dtFinal().isBlank()){
            LOGGER.error("Dados inválidos para consulta de pontos");
            return ResponseEntity.status(400).body("Todos os campos devem ser preenchidos!");
        }
        
        try {
            var response = service.consultarPonto(dto, request);
            LOGGER.info("Consulta de pontos bem sucedida do usuário: {}", dto.login());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException.NotFound e){
            LOGGER.error("Não foram encontrados registros de ponto no período informado para o usuário: {}", dto.login(), e);
            return ResponseEntity.status(404).body("Não foram encontrados registros de ponto no período informado.");
        } catch (HttpClientErrorException.Unauthorized e){
            LOGGER.error("Login inválido. Verifique os dados informados.", e);
            return ResponseEntity.status(401).body("Login inválido. Verifique os dados informados.");
        } catch (Exception e) {
            LOGGER.error("Erro ao consultar ponto", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @PutMapping("/alterar")
    @Operation(summary = "Alterar registro de ponto", description = "Altera um registro de ponto do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ponto alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "ID informado incorreto"),
            @ApiResponse(responseCode = "400", description = "Erro campos inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> alterarPonto(@RequestBody AlterarPontoDto dto, HttpServletRequest request) {
        LOGGER.debug("Alterando ponto do usuário: {}", dto.login());
        try {
            ResponseEntity<?> response = service.alterarPonto(dto, request);
            LOGGER.info("Ponto alterado com sucesso para o usuário: {}", dto.login());
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException.Unauthorized e) {
            return ResponseEntity.status(401).body("Login inválido. Verifique os dados informados.");
        } catch (DateTimeException e){
            return ResponseEntity.badRequest().body("Data ou horário inválidos. Verifique os dados informados.");
        } catch (Exception e) {
            LOGGER.error("Erro ao alterar ponto para o usuário: {}", dto.login(), e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getPontos(HttpServletRequest request) {

        LOGGER.debug("Listando pontos");
        try {
            ResponseEntity<?> response = service.listarPontos(request);
            LOGGER.info("Listagem de pontos realizada com sucesso");
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            LOGGER.error("Erro ao listar pontos", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

}
