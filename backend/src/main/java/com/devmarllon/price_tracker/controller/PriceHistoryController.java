package com.devmarllon.price_tracker.controller;

import com.devmarllon.price_tracker.model.PriceHistory;
import com.devmarllon.price_tracker.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PriceHistoryController {

    private final PriceHistoryRepository repository;

    @GetMapping("/{productId}")
    public List<PriceHistory> getHistoryByProduct(@PathVariable String productId) {
        return repository.findByProductIdOrderByTimestampAsc(productId);
    }
}