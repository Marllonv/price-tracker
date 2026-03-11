package com.devmarllon.price_tracker.controller;

import com.devmarllon.price_tracker.model.StoreConfig;
import com.devmarllon.price_tracker.repository.StoreConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreConfigController {

    private final StoreConfigRepository repository;

    @PostMapping
    public StoreConfig save(@RequestBody StoreConfig config) {
        return repository.save(config);
    }

    @GetMapping
    public List<StoreConfig> getAll() {
        return repository.findAll();
    }
}