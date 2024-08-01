package enigma.todo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public UserNotFoundException() {
        super("User not found");
    }
}
