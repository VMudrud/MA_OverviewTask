package com.epam.resource.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SongMetadataRequestDto {

    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
