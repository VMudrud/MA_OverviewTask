package com.epam.resource.handler;

import com.epam.resource.dto.ErrorResponseDto;
import com.epam.resource.exception.FailedToExtractMetadataException;
import com.epam.resource.exception.FailedToUploadMetadataException;
import com.epam.resource.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(final ResourceNotFoundException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(final ConstraintViolationException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(FailedToExtractMetadataException.class)
    public ResponseEntity<ErrorResponseDto> handleFailedToExtractMetadata(final FailedToExtractMetadataException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(final IllegalArgumentException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(FailedToUploadMetadataException.class)
    public ResponseEntity<ErrorResponseDto> handleFailedToUploadMetadata(final FailedToUploadMetadataException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
