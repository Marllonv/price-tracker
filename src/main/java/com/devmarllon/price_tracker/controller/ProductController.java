package com.devmarllon.price_tracker.controller;

import com.devmarllon.price_tracker.model.Product;
import com.devmarllon.price_tracker.repository.ProductRepository;
import com.devmarllon.price_tracker.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        product.setActive(true);
        if (product.getHistory() == null) {
            product.setHistory(new ArrayList<>());
        }
        Product savedProduct = repository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @PostMapping("/update-now")
    public ResponseEntity<String> forceUpdate() {
        productService.updateAllPrices();
        return ResponseEntity.ok("Processo de atualização disparado manualmente.");
    }

}