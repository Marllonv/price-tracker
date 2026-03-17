package com.devmarllon.price_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "price_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(name = "prod_time_idx", def = "{'productId': 1, 'timestamp': -1}")
public class PriceHistory {

    @Id
    private String id;
    @Indexed
    private String productId;
    private Double price;
    private LocalDateTime timestamp;

}