package com.dublincanvas.dublin_canvas_tracker.repository;

import com.dublincanvas.dublin_canvas_tracker.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    // Search by artist name
    List<Artwork> findByArtistContainingIgnoreCase(String artist);

    // Search by area
    List<Artwork> findByAreaContainingIgnoreCase(String area);

    // Search by artist OR area
    List<Artwork> findByArtistContainingIgnoreCaseOrAreaContainingIgnoreCase(String artist, String area);
}