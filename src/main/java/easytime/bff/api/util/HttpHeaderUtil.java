package easytime.bff.api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.util.Enumeration;

public class HttpHeaderUtil {

    public static HttpHeaders copyHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", "application/json");
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }

        if (headers.containsKey("Content-Length")) {
            headers.remove("Content-Length");
        }

        return headers;
    }
}
