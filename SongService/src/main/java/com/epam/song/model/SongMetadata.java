package com.epam.song.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "song_metadata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongMetadata {

    @Id
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
