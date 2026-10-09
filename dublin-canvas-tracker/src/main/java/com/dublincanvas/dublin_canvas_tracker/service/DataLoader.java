package com.dublincanvas.dublin_canvas_tracker.service;

import com.dublincanvas.dublin_canvas_tracker.entity.Artwork;
import com.dublincanvas.dublin_canvas_tracker.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * DataLoader  runs once at application startup.
 * Reads the Dublin Canvas CSV file and loads all records
 * into the database only if the table is empty.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Override
    public void run(String... args) throws Exception {

        // Only load data if the table is empty
        // This prevents duplicate inserts every time the app restarts
        if (artworkRepository.count() > 0) {
            System.out.println("Data already loaded - skipping CSV import.");
            return;
        }

        System.out.println("Loading Dublin Canvas data from CSV...");

        // Load the CSV from the resources folder
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "dataset.csv");

        if (inputStream == null) {
            System.out.println("CSV file not found skipping data load.");
            return;
        }

        // Read the CSV line by line
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, "windows-1252"));

        String line;
        int count = 0;
        boolean firstLine = true;

        while ((line = reader.readLine()) != null) {

            // Skip the header row
            if (firstLine) {
                firstLine = false;
                continue;
            }

            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }

            try {
                Artwork artwork = parseLine(line);
                if (artwork != null) {
                    artworkRepository.save(artwork);
                    count++;
                }
            } catch (Exception e) {
                // Skip any rows that fail to parse
                System.out.println("Skipping row due to error: " + e.getMessage());
            }
        }

        reader.close();
        System.out.println("CSV import complete. " + count + " artworks loaded.");
    }

    /**
     * Parses a single CSV line into an Artwork object.
     * Handles quoted fields with commas inside them.
     */
    private Artwork parseLine(String line) {
        String[] fields = splitCsvLine(line);

        if (fields.length < 10) return null;

        Artwork artwork = new Artwork();

        
        artwork.setCouncil(cleanField(fields[0]));
        artwork.setArtist(cleanField(fields[4]));
        artwork.setTitle(cleanField(fields[5]));
        artwork.setLocation(cleanField(fields[6]));
        artwork.setArea(cleanField(fields[7]));
        artwork.setStatus(cleanField(fields[9]));

        // Parse year safely
        try {
            String yearStr = cleanField(fields[8]);
            if (!yearStr.isEmpty()) {
                artwork.setYear(Integer.parseInt(yearStr));
            }
        } catch (NumberFormatException e) {
            artwork.setYear(null);
        }

        // Parse latitude safely
        try {
            String latStr = cleanField(fields[1]);
            if (!latStr.isEmpty()) {
                artwork.setLatitude(Double.parseDouble(latStr));
            }
        } catch (NumberFormatException e) {
            artwork.setLatitude(null);
        }

        // Parse longitude safely
        try {
            String lonStr = cleanField(fields[2]);
            if (!lonStr.isEmpty()) {
                artwork.setLongitude(Double.parseDouble(lonStr));
            }
        } catch (NumberFormatException e) {
            artwork.setLongitude(null);
        }

        
        if (fields.length > 10) {
            artwork.setWebsite(cleanField(fields[10]));
        }

        return artwork;
    }

    /**
     * Splits a CSV line correctly, handling quoted fields
     * that may contain commas inside them.
     */
    private String[] splitCsvLine(String line) {
        java.util.List<String> fields = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());

        return fields.toArray(new String[0]);
    }

    /**
     * Trims whitespace and removes surrounding single quotes
     * that appear in the dataset title field.
     */
    private String cleanField(String field) {
        if (field == null) return "";
        String cleaned = field.trim();
        if (cleaned.startsWith("'") && cleaned.endsWith("'")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        return cleaned;
    }
}