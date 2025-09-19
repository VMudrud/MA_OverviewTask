package com.epam.resource.client;

import com.epam.resource.dto.DeletedIdsResponseDto;
import com.epam.resource.dto.IdResponseDto;
import com.epam.resource.dto.SongMetadataRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "song-service", url = "${song.service.url}")
public interface SongServiceClient {

    @PostMapping
    ResponseEntity<IdResponseDto> uploadMetadata(@RequestBody final SongMetadataRequestDto request);

    @DeleteMapping
    ResponseEntity<DeletedIdsResponseDto> deleteMetadata(@RequestParam("id") final String ids);
}
