package com.devmarllon.price_tracker.service;

import com.devmarllon.price_tracker.model.PriceHistory;
import com.devmarllon.price_tracker.model.Product;
import com.devmarllon.price_tracker.repository.ProductRepository;
import com.devmarllon.price_tracker.repository.StoreConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ScraperService scraperService;
    private final StoreConfigRepository storeConfigRepository;

    public void updateAllPrices() {
        List<Product> activeProducts = repository.findByActiveTrue();

        for (Product product : activeProducts) {
            String domain = extractDomain(product.getUrl());
            storeConfigRepository.findByDomain(domain).ifPresent(config -> {
                Double currentPrice = scraperService.getPrice(
                        product.getUrl(),
                        config.getContainerSelector(),
                        config.getMainSelector(),
                        config.getDecimalSelector()
                );

                if (currentPrice != null) {
                    updateProductPrice(product, currentPrice);
                }
            });
        }
    }

    private void updateProductPrice(Product product, Double newPrice) {
        if (!newPrice.equals(product.getLastPrice())) {
            PriceHistory historyEntry = new PriceHistory(newPrice, LocalDateTime.now());
            product.getHistory().add(historyEntry);
            product.setLastPrice(newPrice);

            if (newPrice <= product.getTargetPrice()) {
                System.out.println("Alerta: " + product.getName() + " atingiu o preço alvo.");
                //
            }
        }

        product.setLastUpdate(LocalDateTime.now());
        repository.save(product);
    }

    private String extractDomain(String url) {
        try {
            java.net.URI uri = new java.net.URI(url);
            String host = uri.getHost();
            if (host == null) return "";
            return host.startsWith("www.") ? host.substring(4) : host;
        } catch (Exception e) {
            return "";
        }
    }
}