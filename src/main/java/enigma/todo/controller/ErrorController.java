package enigma.todo.controller;

import enigma.todo.dto.response.ErrorResponse;
import enigma.todo.dto.response.Response;
import enigma.todo.exception.BadCredentialsException;
import enigma.todo.exception.InvalidTokenException;
import enigma.todo.exception.TodoNotFoundException;
import enigma.todo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.error(
                e.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
        return Response.error(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return Response.error(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException e) {
        String message = "";
        if (e.getMessage().contains("users_username_key")) {
            message = "username already exists";
        }
        if (e.getMessage().contains("users_email_key")) {
            message = "email already exists";
        }

        return Response.error(
                message,
                HttpStatus.CONFLICT
        );
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return Response.error(
                e.getMessage()
        );
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTodoNotFoundException(TodoNotFoundException e) {
        return Response.error(
                e.getMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Response.error(
                e.getMessage()
        );
    }
}
