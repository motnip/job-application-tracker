package com.motnip.applicationtracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JobApplicationStateException.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

        Map<String, Object> errorResponse = getErrorResponse(ex,HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    private static Map<String, Object> getErrorResponse(Exception ex, HttpStatus errorCode) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", errorCode.value());
        return errorResponse;
    }
}

