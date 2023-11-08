package com.bnc.price.tester.pricetester.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.ServiceNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class PriceResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleUnmanagedExceptions(
            RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An error occurred";
        String detail = "An error occurred while processing the request. Please contact support";
        ErrorResponseObject response = new ErrorResponseObject(LocalDateTime.now(), message, detail,
                status.getReasonPhrase(), request.getDescription(false), status.value());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgumentExceptions(
            IllegalArgumentException ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An argument has an invalid value";
        String detail = ex.getMessage();
        ErrorResponseObject response = new ErrorResponseObject(LocalDateTime.now(), message, detail,
                status.getReasonPhrase(), request.getDescription(false), status.value());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = { ServiceNotFoundException.class })
    protected ResponseEntity<Object> handleServiceExceptions(
            ServiceNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();
        String detail = "The API requested does not exist";
        ErrorResponseObject response = new ErrorResponseObject(LocalDateTime.now(), message, detail,
                status.getReasonPhrase(), request.getDescription(false), status.value());
        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = { NoPriceFoundException.class })
    protected ResponseEntity<Object> handleNoPriceFoundExceptions(
            RuntimeException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "No Price found with the given criteria";
        String detail = "No Price was found with the given criteria. Please check the request parameters and try again";
        ErrorResponseObject response = new ErrorResponseObject(LocalDateTime.now(), message, detail,
                status.getReasonPhrase(), request.getDescription(false), status.value());
        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }
}
