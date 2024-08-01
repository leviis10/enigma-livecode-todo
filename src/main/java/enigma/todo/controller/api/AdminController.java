package enigma.todo.controller.api;

import enigma.todo.dto.AuthDTO;
import enigma.todo.dto.response.PageableResponse;
import enigma.todo.dto.response.Response;
import enigma.todo.model.Todo;
import enigma.todo.model.User;
import enigma.todo.security.JwtUtils;
import enigma.todo.service.TodoService;
import enigma.todo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final TodoService todoService;
    private final JwtUtils jwtUtils;
    @Value("${super.admin.key}") private String superAdminKey;
    @Value("${admin.key}") private String adminKey;

    @GetMapping("/users")
    public ResponseEntity<PageableResponse<User>> getAllUsers(
            @PageableDefault Pageable pageable
    ) {
        return Response.pageable(userService.getAll(pageable));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<User> updateById(
            @PathVariable Long id,
            @Valid @RequestBody AuthDTO.UpdateRoleDTO updateRoleDTO,
            @RequestHeader(value = "X-Admin-Secret-Key") String adminSecret
    ) {
        if (!validateAdminSecretKey(adminSecret)) {
            throw new RuntimeException("Unauthorized");
        }

        return ResponseEntity.ok(userService.updateById(id, updateRoleDTO));
    }

    @PostMapping("/super-admin")
    public ResponseEntity<User> createSuperAdmin(
            @Valid @RequestBody AuthDTO.RegisterDTO registerDTO,
            @RequestHeader(value = "X-Super-Admin-Secret-Key") String superAdminSecret
    ) {
        if (!validateSuperAdminSecretKey(superAdminSecret)) {
            throw new RuntimeException("UNAUTHORIZED");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createSuperAdmin(registerDTO));
    }

    @GetMapping("/todos")
    public ResponseEntity<PageableResponse<Todo>> getAllTodo(
            @PageableDefault Pageable pageable
    ) {
        return Response.pageable(todoService.getAll(pageable));
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodoById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(todoService.getById(id));
    }

    private boolean validateAdminSecretKey(String adminSecretKey) {
        if (adminSecretKey == null) {
            return false;
        }

        return adminSecretKey.equals(adminKey);
    }

    private boolean validateSuperAdminSecretKey(String superAdminSecret) {
        if (superAdminSecret == null) {
            return false;
        }

        return superAdminSecret.equals(superAdminKey);
    }
}
