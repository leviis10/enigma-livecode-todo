package enigma.todo.service.implementation;

import enigma.todo.dto.AuthDTO;
import enigma.todo.exception.UserNotFoundException;
import enigma.todo.model.User;
import enigma.todo.model.UserRole;
import enigma.todo.repository.UserRepository;
import enigma.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User create(AuthDTO.RegisterDTO registerDTO) {
        User user = User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(UserRole.USER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User updateById(Long id, AuthDTO.UpdateRoleDTO updateRoleDTO) {
        User foundUser = getById(id);
        foundUser.setRole(updateRoleDTO.getRole());
        return userRepository.save(foundUser);
    }

    @Override
    public User createSuperAdmin(AuthDTO.RegisterDTO registerDTO) {
        User user = User.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(registerDTO.getPassword())
                .role(UserRole.SUPER_ADMIN)
                .build();
        return userRepository.save(user);
    }
}
