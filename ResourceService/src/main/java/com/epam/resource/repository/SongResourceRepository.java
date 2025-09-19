package com.epam.resource.repository;

import com.epam.resource.model.SongResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongResourceRepository extends JpaRepository<SongResource, Long> {
}
