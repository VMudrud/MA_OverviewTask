package com.epam.resource.service;

import com.epam.resource.dto.SongMetadataRequestDto;
import com.epam.resource.exception.FailedToExtractMetadataException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SongMetadataService {

    private static final String DURATION_FORMAT = "%02d:%02d";

    public SongMetadataRequestDto extractMetadata(final byte[] data) {
        try (InputStream is = new ByteArrayInputStream(data)) {
            Metadata metadata = new Metadata();
            BodyContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            Mp3Parser Mp3Parser = new Mp3Parser();
            Mp3Parser.parse(is, handler, metadata, context);
            return convertToSongMetadataDto(metadata);
        } catch (Exception e) {
            throw new FailedToExtractMetadataException("Failed to extract metadata from MP3");
        }
    }

    private SongMetadataRequestDto convertToSongMetadataDto(final Metadata metadata) {
        return SongMetadataRequestDto.builder()
                .name(metadata.get("dc:title"))
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .duration(convertDuration(metadata.get("xmpDM:duration")))
                .year(metadata.get("xmpDM:releaseDate"))
                .build();
    }

    private String convertDuration(String s) {
        try {
            double secondsDouble = Double.parseDouble(s.trim());
            long totalSeconds = (long) Math.floor(secondsDouble);
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            return String.format(DURATION_FORMAT, minutes, seconds);
        } catch (Exception e) {
            return null;
        }
    }
}