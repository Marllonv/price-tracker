package com.devmarllon.price_tracker.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
@Data
@Builder
public class Product {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String url;

    private String storeName;

    private Double targetPrice;

    private Double lastPrice;

    private LocalDateTime lastUpdate;

    @Builder.Default
    private List<PriceHistory> history = new ArrayList<>();

    private String userEmail;

    private boolean active = true;
}