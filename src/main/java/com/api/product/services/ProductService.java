package com.api.product.services;

import com.api.product.domain.Product;
import com.api.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getDescrptionForProduct(String description) {
        return productRepository.findByDescription(description);
    }
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }
}
