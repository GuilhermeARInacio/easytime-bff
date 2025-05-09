package easytime.bff.api.service;

import easytime.bff.api.dto.UsuarioDto;
import easytime.bff.api.dto.UsuarioRetornoDto;
import easytime.bff.api.util.HttpHeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
public class UsuarioService {

    private RestTemplate restTemplate;

    @Value("${URL_SRV}")
    private String urlSrv;

    public ResponseEntity<Object> criarUsuario(UsuarioDto dto, HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }

            String url = urlSrv + "users/create";
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            HttpEntity<UsuarioDto> entity = new HttpEntity<>(dto, headers);
            return restTemplate.exchange(url, PUT, entity, Object.class);
        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<List<UsuarioRetornoDto>> listarUsuarios(HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }

            String url = urlSrv + "users/list";
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            return restTemplate.exchange(url, GET, new HttpEntity<>(headers),
                    new ParameterizedTypeReference<List<UsuarioRetornoDto>>() {});

        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<UsuarioRetornoDto> listarUsuarioPorId(Integer id, HttpServletRequest request) {
        try {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }

            String url = urlSrv + "users/getById/" + id;
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            return restTemplate.exchange(url, GET, new HttpEntity<>(headers),
                    UsuarioRetornoDto.class);

        } catch (Exception e) {
            throw e;
        }
    }

    public ResponseEntity<String> deletarUsuario(Integer id, HttpServletRequest request) {
        try {
            if (request == null) {
                restTemplate = new RestTemplate();
            }

            String url = urlSrv + "users/delete/" + id;
            HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

            return restTemplate.exchange(url, DELETE, new HttpEntity<>(headers),
                    String.class);

        } catch (Exception e) {
            throw e;
        }
    }
}
