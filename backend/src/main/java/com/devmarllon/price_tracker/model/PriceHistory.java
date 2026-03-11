package com.devmarllon.price_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PriceHistory {
    private Double price;
    private LocalDateTime timestamp;
}