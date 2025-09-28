package com.epam.resource.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

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
    @JdbcTypeCode(java.sql.Types.BINARY)
    private byte[] data;
}
