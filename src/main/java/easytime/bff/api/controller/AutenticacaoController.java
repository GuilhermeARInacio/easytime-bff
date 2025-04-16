package easytime.bff.api.controller;

import easytime.bff.api.dto.DadosAutenticacao;
import easytime.bff.api.service.AutenticacaoService;
import easytime.bff.api.validacoes.login.ValidacoesLogin;
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

    @PostMapping
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
