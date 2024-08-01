package enigma.todo.service;

import enigma.todo.dto.AuthDTO;
import enigma.todo.model.User;

import java.util.Map;

public interface AuthService {
    User register(AuthDTO.RegisterDTO registerDTO);

    Map<String, String> login(AuthDTO.LoginDTO loginDTO);

    Map<String, String> refresh(AuthDTO.RefreshDTO refreshDTO);
}
