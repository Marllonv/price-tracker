package com.devmarllon.price_tracker.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "store_configs")
@Builder
public class StoreConfig {

    @Id
    private String id;
    private String domain;
    private String containerSelector;
    private String scrapingType;
    private String mainSelector;
    private String decimalSelector;
    private String imageSelector;
    private String nameSelector;

}