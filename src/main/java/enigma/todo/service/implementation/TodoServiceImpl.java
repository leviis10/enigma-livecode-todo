package enigma.todo.service.implementation;

import enigma.todo.dto.TodoDTO;
import enigma.todo.exception.TodoNotFoundException;
import enigma.todo.model.Todo;
import enigma.todo.model.TodoStatus;
import enigma.todo.model.User;
import enigma.todo.repository.TodoRepository;
import enigma.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public Todo create(User user, TodoDTO todoDTO) {
        TodoStatus todoStatus = todoDTO.getStatus() == null ? TodoStatus.PENDING : todoDTO.getStatus();
        Todo todo = Todo.builder()
                .user(user)
                .title(todoDTO.getTitle())
                .description(todoDTO.getDescription())
                .dueDate(todoDTO.getDueDate())
                .status(todoStatus)
                .build();
        return todoRepository.save(todo);
    }

    @Override
    public Page<Todo> getAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @Override
    public Page<Todo> getAllByUser(User user, Pageable pageable) {
        return todoRepository.findAllByUser(pageable, user);
    }

    @Override
    public Todo getById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(TodoNotFoundException::new);
    }

    @Override
    public Todo getByUserAndId(User user, Long id) {
        return todoRepository.findByUserAndId(user, id)
                .orElseThrow(TodoNotFoundException::new);
    }

    @Override
    public Todo updateById(User user, Long id, TodoDTO todoDTO) {
        Todo foundTodo = getByUserAndId(user, id);
        foundTodo.setTitle(todoDTO.getTitle());
        foundTodo.setDescription(todoDTO.getDescription());
        foundTodo.setDueDate(todoDTO.getDueDate());
        foundTodo.setStatus(todoDTO.getStatus());
        return todoRepository.save(foundTodo);
    }

    @Override
    public Todo updateStatusById(User user, Long id, TodoDTO.UpdateStatusDTO updateStatusDTO) {
        Todo foundTodo = getByUserAndId(user, id);
        foundTodo.setStatus(updateStatusDTO.getStatus());
        return todoRepository.save(foundTodo);
    }

    @Override
    public void deleteById(User user, Long id) {
        Todo foundTodo = getByUserAndId(user, id);
        todoRepository.delete(foundTodo);
    }
}
