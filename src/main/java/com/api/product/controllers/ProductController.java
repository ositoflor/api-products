package com.api.product.controllers;

import com.api.product.domain.Product;
import com.api.product.services.ProductService;
import com.api.product.services.execotionhandler.MessageExceptionHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid Product product){
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
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<Product>> getProductForDescription(@PathVariable(value = "description")String description) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getDescriptionForProduct(description));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Product product = productService.findById(id);
        productService.delete(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageExceptionHandler(new Date(),HttpStatus.OK.value(),"Usuário deletado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> upDateProduct(@PathVariable(value = "id")UUID id,
                                                @RequestBody @Valid Product product) {
        Product productRepository = productService.findById(id);
        productRepository.setAmount(product.getAmount());
        productRepository.setValue(product.getValue());
        productRepository.setDescription(product.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productRepository));
    }
}
