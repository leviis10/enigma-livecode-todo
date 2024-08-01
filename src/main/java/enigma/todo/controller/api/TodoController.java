package enigma.todo.controller.api;

import enigma.todo.dto.TodoDTO;
import enigma.todo.dto.response.PageableResponse;
import enigma.todo.dto.response.Response;
import enigma.todo.model.Todo;
import enigma.todo.model.User;
import enigma.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TodoDTO todoDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.create(user, todoDTO));
    }

    @GetMapping
    public ResponseEntity<PageableResponse<Todo>> getAllByUser(
            @AuthenticationPrincipal User user,
            @PageableDefault Pageable pageable
    ) {
        return Response.pageable(todoService.getAllByUser(user, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(todoService.getByUserAndId(user, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO todoDTO
    ) {
        return ResponseEntity.ok(todoService.updateById(user, id, todoDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Todo> updateStatusById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody TodoDTO.UpdateStatusDTO updateStatusDTO
    ) {
        return ResponseEntity.ok(todoService.updateStatusById(user, id, updateStatusDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        todoService.deleteById(user, id);
    }
}
