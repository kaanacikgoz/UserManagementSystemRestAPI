package acikgoz.kaan.UserSecurityAPI.exception;

import acikgoz.kaan.UserSecurityAPI.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException e,
                                                          WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                HttpStatus.NOT_FOUND, e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(error, error.getStatus());
    }

}
