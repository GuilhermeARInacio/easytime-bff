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
import org.springframework.web.client.HttpClientErrorException;

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
            @ApiResponse(responseCode = "404", description = "Formato de senha ou usuário inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    public ResponseEntity autenticar(@RequestBody DadosAutenticacao dto) {
        try{
            validacoes.forEach(v -> v.validar(dto));

            var token = service.autenticar(dto);
            return ResponseEntity.ok(token);
        } catch (HttpClientErrorException e){
            if (e.getStatusCode().is4xxClientError()) {
                if (e.getStatusCode().value() == 401) {
                    return ResponseEntity.status(e.getStatusCode()).body("Usuário não autorizado");
                } if (e.getStatusCode().value() == 404) {
                    return ResponseEntity.status(e.getStatusCode().value()).body("Serviço não encontrado");
                }
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            } if (e.getStatusCode().is5xxServerError()) {
                return ResponseEntity.status(e.getStatusCode()).body("Erro interno do servidor");
            }
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
