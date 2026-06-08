package com.dublincanvas.dublin_canvas_tracker.service;

import com.dublincanvas.dublin_canvas_tracker.entity.Artwork;
import com.dublincanvas.dublin_canvas_tracker.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    public List<Artwork> getAllArtworks() {
        return artworkRepository.findAll();
    }

    public Optional<Artwork> getArtworkById(Long id) {
        return artworkRepository.findById(id);
    }

    public Artwork saveArtwork(Artwork artwork) {
        return artworkRepository.save(artwork);
    }

    public void deleteArtwork(Long id) {
        artworkRepository.deleteById(id);
    }

    public List<Artwork> searchArtworks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return artworkRepository.findAll();
        }
        return artworkRepository
                .findByArtistContainingIgnoreCaseOrAreaContainingIgnoreCase(
                        keyword.trim(), keyword.trim());
    }

    public long getTotalCount() {
        return artworkRepository.count();
    }
}