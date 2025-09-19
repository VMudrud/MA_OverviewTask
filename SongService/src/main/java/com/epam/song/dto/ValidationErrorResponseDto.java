package com.epam.song.dto;

import java.util.Map;

public record ValidationErrorResponseDto(String errorMessage, Map<String, String> details, int errorCode) {
}
