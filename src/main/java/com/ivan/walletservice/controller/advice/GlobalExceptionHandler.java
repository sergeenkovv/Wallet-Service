package com.ivan.walletservice.controller.advice;

import com.ivan.walletservice.dto.ExceptionResponse;
import com.ivan.walletservice.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;

/**
 * GlobalExceptionHandler class that handles exception responses for various exceptions in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles AuthorizationException and returns an Unauthorized response with a custom message.
     *
     * @param e The AuthorizationException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationException(AuthorizationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse("Unauthorized! More information: " + e.getMessage()));
    }

    /**
     * Handles PlayerAlreadyExistsException and returns a Bad Request response with the exception message.
     *
     * @param e The PlayerAlreadyExistsException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handlePlayerAlreadyExistsException(PlayerAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Handles PlayerNotFoundException and returns a Bad Request response with the exception message.
     *
     * @param e The PlayerNotFoundException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePlayerNotFoundException(PlayerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Handles RegistrationException and returns a Bad Request response with a custom message.
     *
     * @param e The RegistrationException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleRegistrationException(RegistrationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse("Unable to register! More information: " + e.getMessage()));
    }

    /**
     * Handles TransactionOperationException and returns a Bad Request response with the exception message.
     *
     * @param e The TransactionOperationException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(TransactionOperationException.class)
    public ResponseEntity<ExceptionResponse> handleTransactionOperationException(TransactionOperationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Handles IllegalArgumentException and returns a Bad Request response with the exception message.
     *
     * @param e The IllegalArgumentException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Handles IllegalStateException and returns a Bad Request response with the exception message.
     *
     * @param e The IllegalStateException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Handles AuthenticationException and returns an Unauthorized response with the exception message.
     *
     * @param e The AuthenticationException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse(e.getMessage()));
    }

    /**
     * Handles BadCredentialsException and returns an Unauthorized response with a custom message.
     *
     * @param e The BadCredentialsException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponse("Invalid user credentials!"));
    }

    /**
     * Handles handleSignatureException and returns a Forbidden response with a custom message.
     *
     * @param e The SignatureException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponse> handleSignatureException(SignatureException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse("You don't have access! More information: " + e.getMessage()));
    }

    /**
     * Exception handler for handling ExpiredJwtException.
     * Returns a response with status code FORBIDDEN and an ExceptionResponse.
     *
     * @param e the ExpiredJwtException thrown when a JWT token expires
     * @return a response with status code FORBIDDEN and an ExceptionResponse
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleSignatureException(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse("Token has expired! More information: " + e.getMessage()));
    }

    /**
     * Handles MethodArgumentNotValidException and returns a Bad Request response with detailed validation error messages.
     *
     * @param e The MethodArgumentNotValidException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        StringBuilder msg = new StringBuilder();
        for (FieldError error : errors) {
            msg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(msg.toString()));
    }

    /**
     * Handles ConstraintViolationException and returns a Bad Request response with detailed constraint violation messages.
     *
     * @param e The ConstraintViolationException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msg.append(constraintViolation.getMessage()).append("; ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(msg.toString()));
    }

    /**
     * Handles NullPointerException and returns a Forbidden response with a custom message.
     *
     * @param e The NullPointerException instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse("Invalid request! Check the request body! More information: " + e.getMessage()));
    }

    /**
     * Handles any general Exception and returns a Not Found response with a custom message.
     *
     * @param e The Exception instance.
     * @return ResponseEntity<ExceptionResponse> containing the response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse("Page not found! More information:" + e.getMessage()));
    }
}