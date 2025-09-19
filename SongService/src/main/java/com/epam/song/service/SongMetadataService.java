package com.epam.song.service;

import com.epam.song.dto.DeleteSongMetadataResponseDto;
import com.epam.song.dto.SongMetadataDto;
import com.epam.song.dto.SongMetadataResponseDto;
import com.epam.song.exception.MetadataNotFoundException;
import com.epam.song.model.SongMetadata;
import com.epam.song.repository.SongMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private final SongMetadataRepository songMetadataRepository;

    public SongMetadataResponseDto uploadMetadata(final SongMetadataDto data) {
        Long id = saveMetadata(data);
        return new SongMetadataResponseDto(id);
    }

    public SongMetadataDto getMetadata(final String id) {
        SongMetadata metadata = songMetadataRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new MetadataNotFoundException(String.format("Metadata with ID: `%s` does not exist.", id)));
        return convertToDto(metadata);
    }

    public DeleteSongMetadataResponseDto deleteMetadata(final String ids) {
        List<Long> idsList = Stream.of(ids.split(","))
                .map(Long::valueOf)
                .toList();
        return new DeleteSongMetadataResponseDto(deleteIfExists(idsList));
    }

    private List<Long> deleteIfExists(final List<Long> idsList) {
        return idsList.stream()
                .filter(songMetadataRepository::existsById)
                .peek(songMetadataRepository::deleteById)
                .toList();
    }

    private SongMetadataDto convertToDto(final SongMetadata metadata) {
        return SongMetadataDto.builder()
                .id(metadata.getId())
                .name(metadata.getName())
                .artist(metadata.getArtist())
                .album(metadata.getAlbum())
                .duration(metadata.getDuration())
                .year(metadata.getYear())
                .build();
    }

    private Long saveMetadata(final SongMetadataDto data) {
        SongMetadata metadata = SongMetadata.builder()
                .id(data.getId())
                .name(data.getName())
                .artist(data.getArtist())
                .album(data.getAlbum())
                .duration(data.getDuration())
                .year(data.getYear())
                .build();
        return songMetadataRepository.save(metadata).getId();
    }
}
