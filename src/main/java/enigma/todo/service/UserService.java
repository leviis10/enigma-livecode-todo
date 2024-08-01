package enigma.todo.service;

import enigma.todo.dto.AuthDTO;
import enigma.todo.dto.response.PageableResponse;
import enigma.todo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User create(AuthDTO.RegisterDTO registerDTO);

    Page<User> getAll(Pageable pageable);

    User getById(Long id);

    User updateById(Long id, AuthDTO.UpdateRoleDTO updateRoleDTO);

    User createSuperAdmin(AuthDTO.RegisterDTO registerDTO);
}
