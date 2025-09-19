package com.epam.resource.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "song_resource")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;
}
