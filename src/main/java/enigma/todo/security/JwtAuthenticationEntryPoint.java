package enigma.todo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import enigma.todo.dto.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.valueOf(response.getStatus()).getReasonPhrase(),
                authException.getMessage()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(response.getStatus());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
