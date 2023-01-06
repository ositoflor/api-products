package com.api.product.repositories;

import com.api.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "select p from Product p where p.description like %?1%")
    Page<Product> findByDescription(String description, Pageable pageable);

    @Query(value = "select p from Product p where p.category.name like %?1%")
    Page<Product> findByCategory(String category, Pageable pageable);

    boolean existsByDescription(String description);
}
