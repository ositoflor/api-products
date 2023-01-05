package com.api.product.controllers;

import com.api.product.domain.Product;
import com.api.product.services.ProductService;
import com.api.product.services.exceptionhandler.MessageExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid Product product){
        if (product.getValue() <= 0) {
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Valor do produto não pode ser menor ou igual a 0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
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
    public ResponseEntity<Page<Product>> getProductForDescription(@PathVariable(value = "description")String description, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getDescriptionForProduct(description, pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> getProductForCategory(@PathVariable(value = "category")String category, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategoryForProduct(category, pageable));
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
        if (product.getValue() <= 0) {
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Valor do produto não pode ser menor ou igual a 0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Product productRepository = productService.findById(id);
        productRepository.setAmount(product.getAmount());
        productRepository.setValue(product.getValue());
        productRepository.setDescription(product.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productRepository));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> upDatePatchProduct(@PathVariable(value = "id")UUID id,
                                                @RequestBody @Valid Product product) {
        if (product.getValue() <= 0) {
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Valor do produto não pode ser menor ou igual a 0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Product productRepository = productService.findById(id);
        productRepository.setAmount(product.getAmount());
        productRepository.setValue(product.getValue());
        productRepository.setDescription(product.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productRepository));
    }
}
