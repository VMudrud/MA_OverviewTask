package com.epam.song.service;

import com.epam.song.dto.SongMetadataDto;
import com.epam.song.exception.MetadataValidationException;
import com.epam.song.exception.SongMetadataAlreadyExistsException;
import com.epam.song.repository.SongMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private static final String ID_VALIDATION_ERROR = "ID '%s' must be a positive integer";
    private static final String ID_ALREADY_EXISTS_ERROR = "Song with ID '%s' already exists";
    private static final String IDS_VALIDATION_ERROR = "IDs must be comma-separated positive integers";
    private static final String IDS_LENGTH_VALIDATION_ERROR = "CSV string length must be less than 200 characters but was %s characters";
    private static final String IDS_VALIDATION_REGEX = "^[1-9]\\d*(?:,[1-9]\\d*)*$";
    private static final String DURATION_VALIDATION_REGEX = "^[0-9]{2,}:[0-5][0-9]$";
    private static final String YEAR_VALIDATION_REGEX = "^(19\\d{2}|20\\d{2})$";
    private static final int MAX_TEXT_LENGTH = 100;

    private final SongMetadataRepository songMetadataRepository;

    public void validateMetadataDto(final SongMetadataDto data) {
        validateIdField(data.getId());
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, Runnable> validators = new LinkedHashMap<>();
        validators.put("name", () -> validateTextField(data.getName(), "name", errors));
        validators.put("artist", () -> validateTextField(data.getArtist(), "artist", errors));
        validators.put("album", () -> validateTextField(data.getAlbum(), "album", errors));
        validators.put("duration", () -> validateDurationField(data.getDuration(), errors));
        validators.put("year", () -> validateYearField(data.getYear(), errors));
        validators.values().forEach(Runnable::run);
        if (!errors.isEmpty()) {
            throw new MetadataValidationException("Validation error", errors);
        }
    }

    public void validateSongId(final String id) {
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

    public void validateSongIds(final String ids) {
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

    private void validateIdField(final Long id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format(IDS_VALIDATION_ERROR));
        }
        if (songMetadataRepository.existsById(id)) {
            throw new SongMetadataAlreadyExistsException(String.format(ID_ALREADY_EXISTS_ERROR, id));
        }
    }

    private void validateTextField(final String value, final String fieldName, final Map<String, String> errors) {
        if (value == null || value.trim().isEmpty()) {
            String message = String.format("%s must not be empty", fieldName);
            errors.put(fieldName, message);
            return;
        }
        int valueLength = value.trim().length();
        if (valueLength > MAX_TEXT_LENGTH) {
            String message = String.format("%s must be between 1 and %d characters but was %d", fieldName, MAX_TEXT_LENGTH, valueLength);
            errors.put(fieldName, message);
        }
    }

    private void validateDurationField(final String duration, final Map<String, String> errors) {
        if (duration == null || duration.trim().isEmpty() || !duration.trim().matches(DURATION_VALIDATION_REGEX)) {
            errors.put("duration", "Duration must be in mm:ss format with leading zeros");
        }
    }

    private void validateYearField(final String year, final Map<String, String> errors) {
        if (year == null || year.trim().isEmpty() || !year.trim().matches(YEAR_VALIDATION_REGEX)) {
            errors.put("year", "Year must be between 1900 and 2099");
        }
    }
}
