package enigma.todo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import enigma.todo.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDTO {
    @Getter
    public static class RegisterDTO {
        @NotBlank(message = "username cannot be blank")
        private String username;

        @NotBlank(message = "email cannot be blank")
        @Email(message = "invalid email")
        private String email;

        @NotBlank(message = "password cannot be blank")
        @Size(min = 12, message = "password is to short")
        private String password;
    }

    @Getter
    public static class LoginDTO {
        @NotBlank(message = "email cannot be blank")
        @Email(message = "invalid email")
        private String email;

        @NotBlank(message = "password cannot be blank")
        @Size(min = 12, message = "password is to short")
        private String password;
    }

    @Getter
    public static class RefreshDTO {
        @NotBlank(message = "refresh token cannot be blank")
        private String refreshToken;
    }

    @Getter
    public static class UpdateRoleDTO {
        private UserRole role;
    }
}