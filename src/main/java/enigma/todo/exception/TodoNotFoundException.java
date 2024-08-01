package enigma.todo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TodoNotFoundException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public TodoNotFoundException() {
        super("Todo not found");
    }
}
