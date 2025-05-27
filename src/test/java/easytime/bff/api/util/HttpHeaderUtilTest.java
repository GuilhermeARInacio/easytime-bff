package easytime.bff.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpHeaderUtilTest {

    @Test
    void copyHeaders_shouldCopyAllHeadersExceptContentLength() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Vector<String> headerNames = new Vector<>();
        headerNames.add("Authorization");
        headerNames.add("Content-Length");
        headerNames.add("Custom-Header");

        when(request.getHeaderNames()).thenReturn(headerNames.elements());
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(request.getHeader("Content-Length")).thenReturn("123");
        when(request.getHeader("Custom-Header")).thenReturn("value");

        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        assertEquals("application/json", headers.getFirst("Content-Type"));
        assertEquals("Bearer token", headers.getFirst("Authorization"));
        assertEquals("value", headers.getFirst("Custom-Header"));
        assertFalse(headers.containsKey("Content-Length"));
    }

    @Test
    void copyHeaders_shouldHandleNoHeaders() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Enumeration<String> emptyEnum = Collections.emptyEnumeration();
        when(request.getHeaderNames()).thenReturn(emptyEnum);

        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        assertEquals("application/json", headers.getFirst("Content-Type"));
        assertEquals(1, headers.size());
    }

    @Test
    void copyHeaders_shouldNotRemoveContentLengthIfNotPresent() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Vector<String> headerNames = new Vector<>();
        headerNames.add("Authorization");
        when(request.getHeaderNames()).thenReturn(headerNames.elements());
        when(request.getHeader("Authorization")).thenReturn("Bearer token");

        HttpHeaders headers = HttpHeaderUtil.copyHeaders(request);

        assertEquals("application/json", headers.getFirst("Content-Type"));
        assertEquals("Bearer token", headers.getFirst("Authorization"));
        assertFalse(headers.containsKey("Content-Length"));
    }
}