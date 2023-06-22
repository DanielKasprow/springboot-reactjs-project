package com.example.demo.controller;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.SqlProductRepository;
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
    private SqlProductRepository productRepository;

    //Add new product to database
    @PostMapping("/product")
    Product newProduct(@RequestBody @Valid Product newProduct) {
        return productRepository.save(newProduct);
    }

    //Get all product from database and sort them by nane
    @GetMapping("/products")
    ResponseEntity<List<Product>> getAllProducts(Pageable page){
        return ResponseEntity.ok(productRepository.findAll(page).getContent());

    }

    //Search product by name
    @GetMapping("/products/search/{searchText}")
    ResponseEntity<List<Product>> getAllProducts(Pageable pageable,@PathVariable String searchText){
        return ResponseEntity.ok(productRepository.findAll(pageable, searchText).getContent());
    }

    //Get product by id to view details
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    //Patch product by id in database
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

    //Delete product by id from database
    @DeleteMapping("/product/{id}")
    String deleteproduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return "product with id " + id + " has been deleted success.";
    }



}