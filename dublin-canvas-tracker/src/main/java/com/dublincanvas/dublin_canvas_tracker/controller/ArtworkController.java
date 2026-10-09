package com.dublincanvas.dublin_canvas_tracker.controller;

import com.dublincanvas.dublin_canvas_tracker.entity.Artwork;
import com.dublincanvas.dublin_canvas_tracker.service.ArtworkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/artworks")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    @GetMapping
    public String listArtworks(
            @RequestParam(value = "search", required = false) String search,
            Model model) {
        List<Artwork> artworks;
        if (search != null && !search.trim().isEmpty()) {
            artworks = artworkService.searchArtworks(search);
            model.addAttribute("search", search);
        } else {
            artworks = artworkService.getAllArtworks();
        }
        model.addAttribute("artworks", artworks);
        model.addAttribute("totalCount", artworks.size());
        return "artwork/list";
    }

    @GetMapping("/{id}")
    public String viewArtwork(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artwork not found: " + id));
        model.addAttribute("artwork", artwork);
        return "artwork/detail";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("formTitle", "Add New Artwork");
        model.addAttribute("submitUrl", "/artworks/new");
        return "artwork/form";
    }

    @PostMapping("/new")
    public String addArtwork(
            @Valid @ModelAttribute("artwork") Artwork artwork,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Add New Artwork");
            model.addAttribute("submitUrl", "/artworks/new");
            return "artwork/form";
        }
        artworkService.saveArtwork(artwork);
        redirectAttributes.addFlashAttribute("successMessage", "Artwork added successfully!");
        return "redirect:/artworks";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artwork not found: " + id));
        model.addAttribute("artwork", artwork);
        model.addAttribute("formTitle", "Edit Artwork");
        model.addAttribute("submitUrl", "/artworks/" + id + "/edit");
        return "artwork/form";
    }

    @PostMapping("/{id}/edit")
    public String updateArtwork(
            @PathVariable Long id,
            @Valid @ModelAttribute("artwork") Artwork artwork,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", "Edit Artwork");
            model.addAttribute("submitUrl", "/artworks/" + id + "/edit");
            return "artwork/form";
        }
        artwork.setId(id);
        artworkService.saveArtwork(artwork);
        redirectAttributes.addFlashAttribute("successMessage", "Artwork updated successfully!");
        return "redirect:/artworks";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteConfirm(@PathVariable Long id, Model model) {
        Artwork artwork = artworkService.getArtworkById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artwork not found: " + id));
        model.addAttribute("artwork", artwork);
        return "artwork/delete-confirm";
    }

    @PostMapping("/{id}/delete")
    public String deleteArtwork(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        artworkService.deleteArtwork(id);
        redirectAttributes.addFlashAttribute("successMessage", "Artwork deleted successfully!");
        return "redirect:/artworks";
    }
}
