package easytime.bff.api.controller;

import easytime.bff.api.dto.pontos.AlterarPontoDto;
import easytime.bff.api.dto.pontos.BaterPonto;
import easytime.bff.api.dto.pontos.ConsultaPontoDTO;
import easytime.bff.api.service.PontoService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> registrarPonto(@RequestBody BaterPonto dto, HttpServletRequest request) {
        LOGGER.debug("Registrando ponto para o usuário");
        try {
            ResponseEntity<?> response = service.registrarPonto(dto, request);
            LOGGER.info("Ponto registrado para o usuário");
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            LOGGER.error("Erro ao registrar ponto para o usuário");
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar ponto", description = "Deleta o ponto de acordo com o id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exclusão do ponto realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ponto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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
            return ResponseEntity.status(response.getStatusCode()).body("Registro de ponto atualizado com sucesso.");
        } catch (HttpClientErrorException.NotFound e){
            return ResponseEntity.status(404).body("ID de ponto não localizado. Verifique se o código está correto.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException.Unauthorized e) {
            return ResponseEntity.status(401).body("Login inválido. Verifique os dados informados.");
        } catch (Exception e) {
            LOGGER.error("Erro ao alterar ponto para o usuário: {}", dto.login(), e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @GetMapping()
    @Operation(summary = "Listar todos os pontos", description = "Retorna uma lista com todos os pontos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pontos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum ponto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> listarAllPontos(HttpServletRequest request) {
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

    @GetMapping("/pedidos")
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos registrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> listarAllPedidos(HttpServletRequest request){
        LOGGER.debug("Listando todos os pedidos de ponto");
        try {
            var response = service.listarPedidos(request);
            LOGGER.info("Listagem de pedidos realizada com sucesso");
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            LOGGER.error("Erro ao listar pedidos", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @GetMapping("/pedidos/pendentes")
    @Operation(summary = "Listar pedidos pendentes", description = "Retorna uma lista com os pedidos pendentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos pendentes retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> listarPedidosPendentes(HttpServletRequest request){
        LOGGER.debug("Listando todos os pedidos de ponto pendentes");
        try {
            var response = service.listarPedidosPendentes(request);
            LOGGER.info("Listagem de pedidos pendentes realizada com sucesso");
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            LOGGER.error("Erro ao listar pedidos pendentes", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @PostMapping("aprovar/{id}")
    @Operation(summary = "Aprovar pedido de ponto", description = "Aprova um pedido de ponto com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido aprovado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum ponto encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> aprovarPonto(@PathVariable Integer id, HttpServletRequest request) {
        LOGGER.debug("Aprovar pedidos pendentes de ponto");
        try {
            var response = service.aprovarPonto(id, request);
            LOGGER.info("Aprovação de pedido realizada com sucesso para pedido com id: {}", id);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            LOGGER.error("Erro ao aprovar pedido", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @PostMapping("reprovar/{id}")
    @Operation(summary = "Reprovar pedido de ponto", description = "Reprova um pedido de ponto com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido reprovado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum ponto encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> reprovarPonto(@PathVariable Integer id, HttpServletRequest request) {
        LOGGER.debug("Reprovar pedidos pendentes de ponto");
        try {
            var response = service.reprovarPonto(id, request);
            LOGGER.info("Recusa de pedido realizada com sucesso para pedido com id: {}", id);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            LOGGER.error("Erro ao listar pedidos recusar pedido", e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

}
