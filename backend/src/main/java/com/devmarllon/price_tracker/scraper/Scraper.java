package com.devmarllon.price_tracker.scraper;

import com.devmarllon.price_tracker.dto.ScrapingResult;
import com.devmarllon.price_tracker.model.StoreConfig;

public interface Scraper {
    ScrapingResult execute(String url, StoreConfig config);

    default Double cleanPrice(String price) {
        if (price == null || price.isBlank()) return null;
        try {
            String cleaned = price.replace("\u00a0", " ")
                    .replaceAll("[^0-9,.]", "")
                    .trim();
            if (cleaned.contains(",") && cleaned.contains(".")) {
                cleaned = cleaned.replace(".", "").replace(",", ".");
            }
            else if (cleaned.contains(",")) {
                cleaned = cleaned.replace(",", ".");
            }
            else if (cleaned.indexOf(".") != cleaned.lastIndexOf(".")) {
                cleaned = cleaned.replace(".", "");
            }
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            System.err.println("Erro ao converter preço: " + price + " | Erro: " + e.getMessage());
            return null;
        }
    }
}