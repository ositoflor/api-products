package com.api.product.services;

import com.api.product.domain.Product;
import com.api.product.repositories.ProductRepository;
import com.api.product.services.execotionhandler.ProductNotFoundException;
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

    public List<Product> getDescriptionForProduct(String description) {
        return productRepository.findByDescription(description);
    }
    public Product findById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ProductNotFoundException());
    }

    public void delete(Product product) {
        findById(product.getId());
        productRepository.delete(product);
    }
}
