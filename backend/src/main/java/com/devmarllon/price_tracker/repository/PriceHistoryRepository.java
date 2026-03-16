package com.devmarllon.price_tracker.repository;

import com.devmarllon.price_tracker.model.PriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PriceHistoryRepository extends MongoRepository<PriceHistory, String> {
    List<PriceHistory> findByProductIdOrderByTimestampDesc(String productId);
}
