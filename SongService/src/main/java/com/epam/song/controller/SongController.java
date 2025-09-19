package com.epam.song.controller;

import com.epam.song.dto.DeleteSongMetadataResponseDto;
import com.epam.song.dto.SongMetadataDto;
import com.epam.song.dto.SongMetadataResponseDto;
import com.epam.song.service.SongMetadataService;
import com.epam.song.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongMetadataService songMetadataService;
    private final ValidationService validationService;

    @PostMapping
    public ResponseEntity<SongMetadataResponseDto> uploadMetadata(@RequestBody final SongMetadataDto request) {
        validationService.validateMetadataDto(request);
        return ResponseEntity.ok(songMetadataService.uploadMetadata(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SongMetadataDto> getSongMetadata(@PathVariable(value = "id") final String id) {
        validationService.validateSongId(id);
        return ResponseEntity.ok(songMetadataService.getMetadata(id));
    }

    @DeleteMapping
    public ResponseEntity<DeleteSongMetadataResponseDto> deleteMetadata(@RequestParam("id") final String ids) {
        validationService.validateSongIds(ids);
        return ResponseEntity.ok(songMetadataService.deleteMetadata(ids));
    }
}
