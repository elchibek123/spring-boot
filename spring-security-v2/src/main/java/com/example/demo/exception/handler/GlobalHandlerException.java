package com.example.demo.exception.handler;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
