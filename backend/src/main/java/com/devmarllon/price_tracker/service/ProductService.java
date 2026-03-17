package com.devmarllon.price_tracker.service;

import com.devmarllon.price_tracker.dto.ScrapingResult;
import com.devmarllon.price_tracker.model.PriceHistory;
import com.devmarllon.price_tracker.model.Product;
import com.devmarllon.price_tracker.repository.PriceHistoryRepository;
import com.devmarllon.price_tracker.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final PriceHistoryRepository priceHistoryRepository; // NOVO
    private final ScraperService scraperService;

    public void updateAllPrices() {
        List<Product> activeProducts = repository.findByActiveTrue();

        for (Product product : activeProducts) {
            try {
                ScrapingResult result = scraperService.scrapeProduct(product);

                if (result != null && result.getPrice() != null) {
                    processPriceUpdate(product, result);
                }
            } catch (Exception e) {
                System.err.println("Erro ao atualizar produto " + product.getId() + ": " + e.getMessage());
            }
        }
    }

    private void processPriceUpdate(Product product, ScrapingResult result) {
        Double newPrice = result.getPrice();
        Double lastPrice = product.getLastPrice();

        if (result.getImageUrl() != null) {
            product.setImageUrl(result.getImageUrl());
        }
        product.setLastUpdate(LocalDateTime.now());

        if (lastPrice == null || Math.abs(newPrice - lastPrice) > 0.001) {

            PriceHistory history = PriceHistory.builder()
                    .productId(product.getId())
                    .price(newPrice)
                    .timestamp(LocalDateTime.now())
                    .build();

            product.setLastPrice(newPrice);

            priceHistoryRepository.save(history);

            if (newPrice <= product.getTargetPrice()) {
                triggerPriceAlert(product);
            }
        }

        repository.save(product);
    }

    private void triggerPriceAlert(Product product) {
        System.out.println("ALERTA: " + product.getName() + " chegou em R$ " + product.getLastPrice());
    }
}