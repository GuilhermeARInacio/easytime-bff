//package easytime.bff.api.infra.security;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class SecurityConfigurationsTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void shouldPermitLoginEndpoint() throws Exception {
//        mockMvc.perform(post("/login"))
//                .andExpect(status().isOk()); // or is4xx if no controller, but not 401/403
//    }
//
//    @Test
//    void shouldPermitSwaggerEndpoints() throws Exception {
//        mockMvc.perform(get("/swagger-ui.html"))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/api-docs/test"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldRequireAuthenticationForOtherEndpoints() throws Exception {
//        mockMvc.perform(get("/protected"))
//                .andExpect(status().isUnauthorized());
//    }
//}