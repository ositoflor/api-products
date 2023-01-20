package com.api.product.services;

import com.api.product.domain.Product;
import com.api.product.repositories.ProductRepository;
import com.api.product.repositories.clients.AuthClient;
import com.api.product.services.exceptionhandler.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthClient authClient;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getDescriptionForProduct(String description, Pageable pageable) {
        return productRepository.findByDescription(description, pageable);
    }
    public Page<Product> getCategoryForProduct(String description, Pageable pageable) {
        return productRepository.findByCategory(description, pageable);
    }
    public Product findById(String id) {
        System.out.print(id);
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ProductNotFoundException());
    }

    public void delete(Product product) {
        findById(product.getId());
        productRepository.delete(product);
    }

    public boolean exitsByDescription(String description) {
        return productRepository.existsByDescription(description);
    }

    public boolean validateToken(String token) {
        return authClient.validToken(token);
    }

    public String getTypeUser(String token) {
        return authClient.getTypeUser(token);
    }
}
