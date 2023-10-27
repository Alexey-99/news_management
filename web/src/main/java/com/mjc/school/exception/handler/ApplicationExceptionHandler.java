package com.mjc.school.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjc.school.config.language.Translator;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private final Translator translator;

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleServiceExceptions(ServiceException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND.value(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(MethodArgumentNotValidException ex) {
        String details = translator.toLocale(ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = translator.toLocale("exception.badRequest");
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.value(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        String details = translator.toLocale("exception.noHandler");
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND.value(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        String details = translator.toLocale("exception.notSupported");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED.value(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}