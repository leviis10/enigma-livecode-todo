package enigma.todo.repository;

import enigma.todo.model.Todo;
import enigma.todo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByUserAndId(User user, Long id);

    Page<Todo> findAllByUser(Pageable pageable, User user);
}
