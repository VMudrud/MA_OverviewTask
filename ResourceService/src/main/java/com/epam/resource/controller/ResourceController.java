package com.epam.resource.controller;

import com.epam.resource.dto.DeletedIdsResponseDto;
import com.epam.resource.dto.IdResponseDto;

import com.epam.resource.service.SongResourceService;
import com.epam.resource.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final SongResourceService songResourceService;
    private final ValidationService validationService;

    @PostMapping
    public ResponseEntity<IdResponseDto> uploadResource(@RequestBody final byte[] data,
                                                        @RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false) final String contentType) {
        validationService.validateContentType(contentType);
        return ResponseEntity.ok(songResourceService.uploadResource(data));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getResource(@PathVariable(value = "id") final String id) {
        validationService.validateResourceId(id);
        byte[] data = songResourceService.getResourceData(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .body(data);
    }

    @DeleteMapping
    public ResponseEntity<DeletedIdsResponseDto> deleteResources(@RequestParam("id") final String ids) {
        validationService.validateResourceIds(ids);
        return ResponseEntity.ok(songResourceService.deleteResources(ids));
    }
}
