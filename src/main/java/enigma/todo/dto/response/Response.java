package enigma.todo.dto.response;

import enigma.todo.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class Response {
    public static <T> ResponseEntity<SuccessResponse<T>> success(T data, String message, HttpStatus httpStatus) {
        SuccessResponse<T> response = SuccessResponse.<T>builder()
                .data(data)
                .status(httpStatus.value())
                .message(message)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static <T> ResponseEntity<SuccessResponse<T>> success(T data, String message) {
        SuccessResponse<T> response = SuccessResponse.<T>builder()
                .data(data)
                .status(HttpStatus.OK.value())
                .message(message)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static <T> ResponseEntity<SuccessResponse<T>> success(T data, HttpStatus httpStatus) {
        SuccessResponse<T> response = SuccessResponse.<T>builder()
                .data(data)
                .status(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static <T> ResponseEntity<SuccessResponse<T>> success(T data) {
        SuccessResponse<T> response = SuccessResponse.<T>builder()
                .data(data)
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static ResponseEntity<ErrorResponse> error(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(ErrorResponse.builder()
                .error(message)
                .message(message)
                .build()
        );
    }

    public static ResponseEntity<ErrorResponse> error(String message) {
        return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<PageableResponse<T>> pageable(Page<T> pageable, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(PageableResponse.<T>builder()
                .items(pageable.getContent())
                .totalItems(pageable.getTotalElements())
                .currentPage(pageable.getNumber())
                .totalPages(pageable.getTotalPages())
                .build()
        );
    }

    public static <T> ResponseEntity<PageableResponse<T>> pageable(Page<T> pageable) {
        return pageable(pageable, HttpStatus.OK);
    }
}
