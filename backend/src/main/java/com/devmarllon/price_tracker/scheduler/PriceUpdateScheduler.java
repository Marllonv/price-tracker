package com.devmarllon.price_tracker.scheduler;

import com.devmarllon.price_tracker.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceUpdateScheduler {

    private final ProductService productService;

    @Scheduled(fixedDelay = 1800000)
    public void checkPricesJob() {
        System.out.println("Iniciando rotina automática de verificação de preços.");
        productService.updateAllPrices();
        System.out.println("Rotina finalizada com sucesso.");
    }
}