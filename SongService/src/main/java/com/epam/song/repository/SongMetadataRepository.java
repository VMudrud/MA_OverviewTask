package com.epam.song.repository;

import com.epam.song.model.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongMetadataRepository extends JpaRepository<SongMetadata, Long> {
}
