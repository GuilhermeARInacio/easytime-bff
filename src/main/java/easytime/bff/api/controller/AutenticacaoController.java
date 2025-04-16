package easytime.bff.api.controller;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.service.AutenticacaoService;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/login")
@RestController
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService service;

    @Autowired
    private List<ValidacoesLogin> validacoes;

    @PostMapping
    @Operation(summary = "Logar com um usuário", description = "Retorna um token valido para acessar os outros endpoints da API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um token JWT"),
            @ApiResponse(responseCode = "400", description = "Formato de senha ou usuário inválido")
    })
    public ResponseEntity autenticar(@RequestBody DadosAutenticacao dto) {
        try{
            validacoes.forEach(v -> v.validar(dto));

            String tokenJWT = service.autenticar(dto);

            return ResponseEntity.ok(tokenJWT);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
