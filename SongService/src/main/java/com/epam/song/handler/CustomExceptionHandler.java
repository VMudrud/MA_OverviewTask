package com.epam.song.handler;

import com.epam.song.dto.ErrorResponseDto;

import com.epam.song.dto.ValidationErrorResponseDto;
import com.epam.song.exception.MetadataNotFoundException;
import com.epam.song.exception.MetadataValidationException;
import com.epam.song.exception.SongMetadataAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MetadataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMetadataNotFound(final MetadataNotFoundException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MetadataValidationException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleMetadataValidation(final MetadataValidationException ex) {
        ValidationErrorResponseDto body = new ValidationErrorResponseDto(ex.getMessage(), ex.getErrors(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(final ConstraintViolationException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(final IllegalArgumentException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(SongMetadataAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleSongMetadataAlreadyExists(final SongMetadataAlreadyExistsException ex) {
        ErrorResponseDto body = new ErrorResponseDto(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
