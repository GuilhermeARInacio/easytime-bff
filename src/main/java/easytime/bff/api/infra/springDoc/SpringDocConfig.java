package easytime.bff.api.infra.springDoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springDocOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Easytime - Sistema de marcação de ponto (BFF)")
                        .version("1.0")
                        .description("""
                                Easytime é um sistema de marcação de ponto desenvolvido com uma arquitetura BFF
                                (Back For Front). Este projeto é responsável por gerenciar a autenticação de
                                usuários e fornecer um token JWT para acesso seguro às funcionalidades do sistema.
                                """));
    }


//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiDetails())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiDetails() {
//        return new ApiInfo(
//                "Easytime - Sistema de marcação de ponto (BFF)",
//                """
//                        Easytime é um sistema de marcação de ponto desenvolvido com uma arquitetura BFF
//                        (Back For Front). Este projeto é responsável por gerenciar a autenticação de
//                        usuários e fornecer um token JWT para acesso seguro às funcionalidades do sistema.
//                        """,
//                "1.0",
//                "Termos de serviço",
//                new Contact("Nome do desenvolvedor", "url do desenvolvedor", "email do desenvolvedor"),
//                "Licença da API",
//                "URL da licença",
//                Collections.emptyList());
//    }
}
