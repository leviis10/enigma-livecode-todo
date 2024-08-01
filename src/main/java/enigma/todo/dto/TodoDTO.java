package enigma.todo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import enigma.todo.model.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDTO {
    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotNull(message = "due date cannot be null")
    private LocalDate dueDate;

    private TodoStatus status;

    @Getter
    public static class UpdateStatusDTO {
        @NotNull(message = "todo status cannot be null")
        private TodoStatus status;
    }
}
