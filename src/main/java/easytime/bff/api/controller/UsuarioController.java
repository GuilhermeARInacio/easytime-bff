package easytime.bff.api.controller;

import easytime.bff.api.dto.usuario.UsuarioDto;
import easytime.bff.api.dto.usuario.UsuarioRetornoDto;
import easytime.bff.api.service.UsuarioService;
import easytime.bff.api.util.ExceptionHandlerUtil;
import easytime.bff.api.validacoes.cadastro.ValidacoesCadastro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Criar usuário", description = "Envia um UsuarioDto para api e cria um usuário no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Formato dos campos inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto dto, HttpServletRequest request) {
        LOGGER.debug("Iniciando o cadastro para o usuário: {}", dto.login());
        try {
            if(dto.nome() == null || dto.email() == null || dto.login() == null || dto.password() == null || dto.sector() == null || dto.jobTitle() == null || dto.role() == null) {
                return ResponseEntity.badRequest().body("Preencha todos os campos.");
            }

            validacoes.forEach(v -> v.validar(dto));

            LOGGER.info("Cadastro bem sucedido para o usuário: {}", dto.login());
            ResponseEntity<Object> response = service.criarUsuario(dto, request);
            return ResponseEntity.status(response.getStatusCodeValue()).body(response.getBody());
        } catch (Exception e) {
            LOGGER.error("Erro ao cadastrar o usuário: {}", dto.login(), e);
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @GetMapping("/list")
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista todos os usuários cadastrados"),
            @ApiResponse(responseCode = "400", description = "Não há usuários cadastrados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity<?> listarUsuarios(HttpServletRequest request) {
        LOGGER.debug("Listando todos os usuários");
        try {
            ResponseEntity<List<UsuarioRetornoDto>> response = service.listarUsuarios(request);
            LOGGER.debug("Listagem de usuários realizada com sucesso.");
            return ResponseEntity.status(response.getStatusCodeValue()).body(response.getBody());
        } catch (Exception e){
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @GetMapping("/getById/{id}")
    @Operation(summary = "Encontrar usuário pelo id", description = "Retorna um usuário de acordo com o id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário referente ao id informado"),
            @ApiResponse(responseCode = "400", description = "Não há um usuário com esse id cadastrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity<?> listarUsuarios(@PathVariable Integer id, HttpServletRequest request) {
        LOGGER.debug("Listando usuário com id: {}", id);
        try {
            ResponseEntity<UsuarioRetornoDto> response = service.listarUsuarioPorId(id, request);
            LOGGER.info("Listagem bem sucedida para o id: {}", id);
            return ResponseEntity.status(response.getStatusCodeValue()).body(response.getBody());
        } catch (Exception e){
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deleta usuário pelo id", description = "Deleta um usuário de acordo com o id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleta o usuário referente ao id informado"),
            @ApiResponse(responseCode = "400", description = "Não há um usuário com esse id cadastrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id, HttpServletRequest request) {
        LOGGER.debug("Deletando usuário com id: {}", id);
        try {
            ResponseEntity<String> response = service.deletarUsuario(id, request);
            LOGGER.info("Exclusão bem sucedido do usuario com id: {}", id);
            return ResponseEntity.status(response.getStatusCodeValue()).body(response.getBody());
        } catch (Exception e){
            return ExceptionHandlerUtil.tratarExcecao(e, LOGGER);
        }
    }
}
