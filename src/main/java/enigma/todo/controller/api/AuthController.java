package enigma.todo.controller.api;

import enigma.todo.dto.AuthDTO;
import enigma.todo.dto.response.Response;
import enigma.todo.dto.response.SuccessResponse;
import enigma.todo.model.User;
import enigma.todo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody AuthDTO.RegisterDTO registerDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
        @Valid @RequestBody AuthDTO.LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(
            @Valid @RequestBody AuthDTO.RefreshDTO refreshDTO
    ) {
        return ResponseEntity.ok(authService.refresh(refreshDTO));
    }
}
