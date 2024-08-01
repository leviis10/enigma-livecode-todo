package enigma.todo.service;

import enigma.todo.dto.TodoDTO;
import enigma.todo.model.Todo;
import enigma.todo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {
    Todo create(User user, TodoDTO todoDTO);

    Page<Todo> getAll(Pageable pageable);

    Page<Todo> getAllByUser(User user, Pageable pageable);

    Todo getById(Long id);

    Todo getByUserAndId(User user, Long id);

    Todo updateById(User user, Long id, TodoDTO todoDTO);

    Todo updateStatusById(User user, Long id, TodoDTO.UpdateStatusDTO updateStatusDTO);

    void deleteById(User user, Long id);
}
