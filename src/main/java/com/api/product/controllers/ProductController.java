package com.api.product.controllers;

import com.api.product.domain.Product;
import com.api.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody Product product){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllProduct(Pageable pageable) {
        return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable(value = "id")UUID id) {
        Optional<Product> product = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<Product>> getProductForDescription(@PathVariable(value = "description")String description) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getDescrptionForProduct(description));
    }

}
