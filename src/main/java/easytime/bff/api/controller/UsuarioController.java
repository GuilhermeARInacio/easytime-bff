package easytime.bff.api.controller;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.service.AutenticacaoService;
import easytime.bff.api.service.UsuarioService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import easytime.bff.api.validacoes.cadastro.ValidacoesCadastro;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private List<ValidacoesCadastro> validacoes;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    @PutMapping("/create")
    public ResponseEntity criarUsuario(@RequestBody UsuarioDto dto, HttpServletRequest request) {
        LOGGER.debug("Iniciando o cadastro para o usuário: {}", dto.login());

        if(dto == null || dto.nome() == null || dto.email() == null || dto.login() == null || dto.password() == null || dto.sector() == null || dto.jobTitle() == null || dto.role() == null) {
            return ResponseEntity.badRequest().body("Preencha todos os campos.");
        }
        try {
            validacoes.forEach(v -> v.validar(dto));

            LOGGER.debug("Cadastro bem sucedido para o usuário: {}", dto.login());
            ResponseEntity<Object> response = service.criarUsuario(dto, request);
            return ResponseEntity.status(response.getStatusCodeValue()).body(response.getBody());
        } catch (Exception e) {
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }
}
