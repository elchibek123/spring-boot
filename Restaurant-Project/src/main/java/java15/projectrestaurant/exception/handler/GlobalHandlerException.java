package java15.projectrestaurant.exception.handler;

import jakarta.validation.ValidationException;
import java15.projectrestaurant.dto.response.ExceptionResponse;
import java15.projectrestaurant.enums.RestaurantType;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.exception.ForbiddenException;
import java15.projectrestaurant.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse getNotFoundException(NotFoundException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse getForbiddenException(ForbiddenException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse getBadRequestException(BadRequestException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse getValidationException(ValidationException e) {
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .className(e.getClass().getName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof MethodArgumentTypeMismatchException ||
                cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid value provided for RestaurantType. Accepted values are: " +
                            Arrays.toString(RestaurantType.values()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body.");
    }
}