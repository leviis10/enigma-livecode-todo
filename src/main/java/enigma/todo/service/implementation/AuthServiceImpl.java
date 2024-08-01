package enigma.todo.service.implementation;

import enigma.todo.dto.AuthDTO;
import enigma.todo.exception.BadCredentialsException;
import enigma.todo.exception.InvalidTokenException;
import enigma.todo.model.User;
import enigma.todo.security.JwtUtils;
import enigma.todo.service.AuthService;
import enigma.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public User register(AuthDTO.RegisterDTO registerDTO) {
        User savedUser = userService.create(registerDTO);
        savedUser.setId(null);
        savedUser.setRole(null);
        savedUser.setCreatedAt(null);
        return savedUser;
    }

    @Override
    public Map<String, String> login(AuthDTO.LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(),
                    loginDTO.getPassword()
            ));
            return Map.of(
                    "accessToken", jwtUtils.generateAccessToken(loginDTO.getEmail()),
                    "refreshToken", jwtUtils.generateRefreshToken(loginDTO.getEmail())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("invalid credentials");
        }
    }

    @Override
    public Map<String, String> refresh(AuthDTO.RefreshDTO refreshDTO) {
        if (!jwtUtils.validateRefreshToken(refreshDTO.getRefreshToken())) {
            throw new InvalidTokenException("invalid refresh token");
        }

        String email = jwtUtils.getEmailFromToken(refreshDTO.getRefreshToken());
        return Map.of(
                "accessToken", jwtUtils.generateAccessToken(email)
        );
    }
}
