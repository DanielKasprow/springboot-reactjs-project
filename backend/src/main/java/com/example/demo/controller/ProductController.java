package com.example.demo.controller;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    Product newProduct(@RequestBody @Valid Product newProduct) {
        return productRepository.save(newProduct);
    }

    @GetMapping("/products")
    ResponseEntity<List<Product>> getAllProducts(Pageable page){
        return ResponseEntity.ok(productRepository.findAll(page).getContent());

    }
    @GetMapping("/products/search/{searchText}")
    ResponseEntity<List<Product>> getAllProducts(Pageable pageable,@PathVariable String searchText){
        return ResponseEntity.ok(productRepository.findAll(pageable, searchText).getContent());
    }
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/product/{id}")
    Product updateProduct(@RequestBody Product newproduct, @PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newproduct.getName());
                    product.setDescription(newproduct.getDescription());
                    product.setPrice(newproduct.getPrice());
                    return productRepository.save(product);
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/product/{id}")
    String deleteproduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return "product with id " + id + " has been deleted success.";
    }



}