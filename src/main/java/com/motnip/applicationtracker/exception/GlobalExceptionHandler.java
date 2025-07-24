package com.motnip.applicationtracker.exception;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(JobApplicationStateException.class)
    protected ResponseEntity<Object> handleConflict(Exception ex) {

        Map<String, Object> errorResponse = getErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ":" + error.getDefaultMessage())
                .reduce("", (a, b) -> a.isBlank() ? b : (a + b));

        ex.getBody().setDetail(errorMessage);

        return handleExceptionInternal(ex, null, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> createResponseEntity(
            @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        ProblemDetail problemDetail = (ProblemDetail) body;

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", Optional.ofNullable(problemDetail.getDetail()).orElse("Invalid request"));
        errorResponse.put("status", problemDetail.getStatus());

        return new ResponseEntity<>(errorResponse, headers, statusCode);
    }

    private static Map<String, Object> getErrorResponse(String errorMessage, int httpStatuCode) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", errorMessage);
        errorResponse.put("status", httpStatuCode);
        return errorResponse;
    }
}
