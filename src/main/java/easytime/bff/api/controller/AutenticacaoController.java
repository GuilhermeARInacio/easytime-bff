package easytime.bff.api.controller;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.service.AutenticacaoService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/login")
@RestController
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService service;

    @Autowired
    private List<ValidacoesLogin> validacoes;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoController.class);

    @PostMapping
    @Operation(summary = "Logar com um usuário", description = "Retorna um token valido para acessar os outros endpoints da API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um token JWT"),
            @ApiResponse(responseCode = "400", description = "Formato de senha ou usuário inválido, ou campos vazios"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity<?> autenticar(@RequestBody DadosAutenticacao dto) {
        LOGGER.debug("Iniciando autenticação para o usuário: {}", dto.usuario());

        if (dto == null || dto.senha() == null || dto.usuario() == null) {
            return ResponseEntity.badRequest().body("Preencha todos os campos.");
        }
        try {
            validacoes.forEach(v -> v.validar(dto));

            LOGGER.info("Autenticação bem-sucedida para o usuário: {}", dto.usuario());
            return ResponseEntity.ok(service.autenticar(dto));

        } catch (Exception e) {
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

}
