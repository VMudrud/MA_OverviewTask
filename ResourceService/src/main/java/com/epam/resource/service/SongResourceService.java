package com.epam.resource.service;

import com.epam.resource.client.SongServiceClient;
import com.epam.resource.dto.DeletedIdsResponseDto;
import com.epam.resource.dto.IdResponseDto;
import com.epam.resource.dto.SongMetadataRequestDto;
import com.epam.resource.exception.FailedToUploadMetadataException;
import com.epam.resource.exception.ResourceNotFoundException;
import com.epam.resource.model.SongResource;
import com.epam.resource.repository.SongResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongResourceService {

    private final SongMetadataService songMetadataService;
    private final SongResourceRepository songResourceRepository;
    private final SongServiceClient songServiceClient;

    public IdResponseDto uploadResource(final byte[] data) {
        SongMetadataRequestDto metadata = songMetadataService.extractMetadata(data);
        Long id = saveResource(data);
        uploadMetadata(metadata, id);
        return new IdResponseDto(id);
    }

    public byte[] getResourceData(final String id) {
        SongResource resource = songResourceRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource with ID: `%s` does not exist.", id)));
        return resource.getData();
    }

    public DeletedIdsResponseDto deleteResources(final String ids) {
        List<Long> idsList = Stream.of(ids.split(","))
                .map(Long::valueOf)
                .toList();
        return new DeletedIdsResponseDto(deleteIfExists(idsList));
    }

    private void uploadMetadata(final SongMetadataRequestDto metadata, final Long id) {
        metadata.setId(id);
        try {
            songServiceClient.uploadMetadata(metadata);
        } catch (Exception exception) {
            deleteCreatedResource(id);
            log.error("Error while uploading metadata for resource ID: {}", id, exception);
            throw new FailedToUploadMetadataException("The request body is invalid MP3.");
        }
    }

    private Long saveResource(final byte[] data) {
        SongResource resource = new SongResource();
        resource.setData(data);
        return songResourceRepository.save(resource).getId();
    }

    private List<Long> deleteIfExists(final List<Long> idsList) {
        return idsList.stream()
                .filter(songResourceRepository::existsById)
                .peek(songResourceRepository::deleteById)
                .peek(this::deleteMetadata)
                .toList();
    }

    private void deleteCreatedResource(final Long id) {
        try {
            songResourceRepository.deleteById(id);
        } catch (Exception exception) {
            log.error("Error occurred while deleting resource with ID: {}", id, exception);
        }
    }

    private void deleteMetadata(final Long id) {
        try {
            songServiceClient.deleteMetadata(String.valueOf(id));
        } catch (Exception exception) {
            log.error("Error occurred while deleting metadata with ID: {}", id, exception);
        }
    }
}
