package com.epam.resource.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private static final String ID_VALIDATION_ERROR = "ID '%s' must be a positive integer";
    private static final String IDS_VALIDATION_ERROR = "IDs must be comma-separated positive integers";
    private static final String IDS_LENGTH_VALIDATION_ERROR = "CSV string length must be less than 200 characters but was %s characters";
    private static final String MP3_VALIDATION_ERROR = "Only 'audio/mpeg' content type is supported";
    private static final String IDS_VALIDATION_REGEX = "^[1-9]\\d*(?:,[1-9]\\d*)*$";

    public void validateResourceId(final String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format(ID_VALIDATION_ERROR, id));
        }
        try {
            long value = Long.parseLong(id);
            if (value <= 0) {
                throw new IllegalArgumentException(String.format(ID_VALIDATION_ERROR, id));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(ID_VALIDATION_ERROR, id), e);
        }
    }

    public void validateResourceIds(final String ids) {
        if (ids == null || ids.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format(IDS_VALIDATION_ERROR));
        }
        if (ids.length() >= 200) {
            throw new IllegalArgumentException(String.format(IDS_LENGTH_VALIDATION_ERROR, ids.length()));
        }
        if (!ids.matches(IDS_VALIDATION_REGEX)) {
            throw new IllegalArgumentException(String.format(IDS_VALIDATION_ERROR));
        }
    }

    public void validateContentType(final String contentType) {
        if (contentType == null || !contentType.equals("audio/mpeg")) {
            throw new IllegalArgumentException(String.format(MP3_VALIDATION_ERROR));
        }
    }
}
