package com.devmarllon.price_tracker.scraper;

import com.devmarllon.price_tracker.scraper.strategy.JsoupScraper;
import com.devmarllon.price_tracker.scraper.strategy.SeleniumScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScraperFactory {

    private final SeleniumScraper seleniumScraper;
    private final JsoupScraper jsoupScraper;

    public Scraper getScraper(String type) {
        if ("D".equalsIgnoreCase(type)) {
            return seleniumScraper;
        }
        return jsoupScraper;
    }
}