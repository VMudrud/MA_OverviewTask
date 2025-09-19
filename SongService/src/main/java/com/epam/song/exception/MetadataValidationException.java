package com.epam.song.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class MetadataValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public MetadataValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
