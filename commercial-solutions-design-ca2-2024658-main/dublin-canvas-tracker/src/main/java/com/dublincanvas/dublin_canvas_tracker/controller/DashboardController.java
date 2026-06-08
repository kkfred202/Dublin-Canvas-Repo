package com.dublincanvas.dublin_canvas_tracker.controller;

import com.dublincanvas.dublin_canvas_tracker.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class DashboardController {

    @Autowired
    private ArtworkService artworkService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        model.addAttribute("totalArtworks", artworkService.getTotalCount());
        model.addAttribute("username", principal.getName());
        return "dashboard";
    }
}