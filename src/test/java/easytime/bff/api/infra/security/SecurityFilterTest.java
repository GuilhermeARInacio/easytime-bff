package easytime.bff.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityFilterTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private SecurityFilter securityFilter;

    public SecurityFilterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_withValidToken_setsAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validtoken");
        when(tokenService.validate("validtoken")).thenReturn(true);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withInvalidToken_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
        when(tokenService.validate("invalidtoken")).thenReturn(false);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withoutToken_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void recuperarToken_returnsTokenWithoutBearer() {
        when(request.getHeader("Authorization")).thenReturn("Bearer mytoken");
        String token = securityFilter.recuperarToken(request);
        assertEquals("mytoken", token);
    }

    @Test
    void recuperarToken_returnsNullIfNoHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);
        String token = securityFilter.recuperarToken(request);
        assertNull(token);
    }
}