package com.devmarllon.price_tracker.service;

import com.devmarllon.price_tracker.dto.ScrapingResult;
import com.devmarllon.price_tracker.model.Product;
import com.devmarllon.price_tracker.model.StoreConfig;
import com.devmarllon.price_tracker.repository.StoreConfigRepository;
import com.devmarllon.price_tracker.scraper.ScraperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class ScraperService {

    private final StoreConfigRepository storeConfigRepository;
    private final ScraperFactory scraperFactory;


    public ScrapingResult scrapeProduct(Product product) {
        String domain = extractDomain(product.getUrl());

        StoreConfig config = storeConfigRepository.findByDomain(domain)
                .orElseThrow(() -> new RuntimeException("Configuração não encontrada para o domínio: " + domain));

        return scraperFactory.getScraper(config.getScrapingType())
                .execute(product.getUrl(), config);
    }

    private String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            if (domain == null) return null;
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (URISyntaxException e) {
            throw new RuntimeException("URL inválida: " + url);
        }
    }
}