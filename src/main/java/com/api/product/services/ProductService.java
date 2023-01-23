package com.api.product.services;

import com.api.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product save(Product product);
    Page<Product> findAll(Pageable pageable);
    Page<Product> getDescriptionForProduct(String description, Pageable pageable);
    Page<Product> getCategoryForProduct(String description, Pageable pageable);
    Product findById(String id);
    void delete(Product product);
    boolean exitsByDescription(String description);
    boolean validateToken(String token);
    String getTypeUser(String token);
}
