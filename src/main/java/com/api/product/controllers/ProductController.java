package com.api.product.controllers;

import com.api.product.domain.Product;
import com.api.product.services.impl.ProductServiceImpl;
import com.api.product.services.exceptionhandler.MessageExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;


    @PostMapping(value = "/cadastrar")
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid Product product,
                                              @RequestHeader(HttpHeaders.AUTHORIZATION)String token){
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };

        if (productService.getTypeUser(token).equals("CLIENT")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageExceptionHandler(new Date(), HttpStatus.FORBIDDEN.value(), "Usuário sem permissão" ));
        }

        if (productService.exitsByDescription(product.getDescription())){
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Produto já cadastrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        if (product.getAmount() <= 0 || product.getValue() <= 0) {
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Preço ou quantidade não pode ser 0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllProduct(Pageable pageable,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION)String token) {
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };
        return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")String id,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION)String token) {
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<Object> getProductForDescription(@PathVariable(value = "description")String description, Pageable pageable,
                                                                  @RequestHeader(HttpHeaders.AUTHORIZATION)String token) {
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };
        return ResponseEntity.status(HttpStatus.OK).body(productService.getDescriptionForProduct(description, pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> getProductForCategory(@PathVariable(value = "category")String category, Pageable pageable,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION)String token) {
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };
        return ResponseEntity.status(HttpStatus.OK).body(productService.getCategoryForProduct(category, pageable));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") String id,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION)String token) {
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };

        if (productService.getTypeUser(token).equals("CLIENT")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageExceptionHandler(new Date(), HttpStatus.FORBIDDEN.value(), "Usuário sem permissão" ));
        }
        Product product = productService.findById(id);
        productService.delete(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageExceptionHandler(new Date(),HttpStatus.OK.value(),"Usuário deletado com sucesso"));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> upDateProduct(@PathVariable(value = "id")String id,
                                                @RequestBody @Valid Product product,
                                                @RequestHeader(HttpHeaders.AUTHORIZATION)String token) {
        if (!productService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageExceptionHandler(new Date(), HttpStatus.UNAUTHORIZED.value(), "Token Inválido" ));
        };

        if (productService.getTypeUser(token).equals("CLIENT")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageExceptionHandler(new Date(), HttpStatus.FORBIDDEN.value(), "Usuário sem permissão" ));
        }
        if (product.getValue() <= 0 && product.getAmount() <= 0) {
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Valor ou quantidade do produto não pode ser menor ou igual a 0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Product productRepository = productService.findById(id);
        productRepository.setAmount(product.getAmount());
        productRepository.setValue(product.getValue());
        productRepository.setDescription(product.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productRepository));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> upDatePatchProduct(@PathVariable(value = "id")String id,
                                                @RequestBody @Valid Product product) {
        if (product.getValue() <= 0 && product.getAmount() <= 0) {
            MessageExceptionHandler error = new MessageExceptionHandler(new Date(), HttpStatus.NOT_FOUND.value(), "Valor ou quantidade do produto não pode ser menor ou igual a 0");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Product productRepository = productService.findById(id);
        productRepository.setAmount(product.getAmount());
        productRepository.setValue(product.getValue());
        productRepository.setDescription(product.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productRepository));
    }
}
