package com.dublincanvas.dublin_canvas_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "artworks")
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Council field 
    @NotBlank(message = "Council is required")
    @Size(max = 150, message = "Council name must not exceed 150 characters")
    @Column(nullable = false)
    private String council;

    @NotBlank(message = "Artist name is required")
    @Size(max = 150, message = "Artist name must not exceed 150 characters")
    @Column(nullable = false)
    private String artist;

    @NotBlank(message = "Artwork title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must not exceed 255 characters")
    @Column(nullable = false)
    private String location;

    
    @Size(max = 100, message = "Area must not exceed 100 characters")
    private String area;

    // Year validated between 1990 and 2030 using @Min and @Max
    @Min(value = 1990, message = "Year must be 1990 or later")
    @Max(value = 2030, message = "Year must be 2030 or earlier")
    private Integer year;

    // Status values from dataset
    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;

    // Link to the Dublin Canvas artist profile page
    @Size(max = 500, message = "Website URL must not exceed 500 characters")
    private String website;

    // GPS coordinates from the original dataset
    private Double latitude;
    private Double longitude;

   
    public Artwork() {}

    // Manual getters and setters to replace Lombok annotations
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCouncil() { return council; }
    public void setCouncil(String council) { this.council = council; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}