package easytime.bff.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/health")
public class HealthController {

    @RequestMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Bem-vindo ao EasyTime!");
    }
}
