package com.devmarllon.price_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrapingResult {
    private Double price;
    private String name;
    private String imageUrl;
}
