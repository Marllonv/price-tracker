package com.devmarllon.price_tracker.repository;

import com.devmarllon.price_tracker.model.StoreConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreConfigRepository extends MongoRepository<StoreConfig, String> {

    Optional<StoreConfig> findByDomain(String domain);
}