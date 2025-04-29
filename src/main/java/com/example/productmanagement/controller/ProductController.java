package com.example.productmanagement.controller;

import com.example.productmanagement.bean.Response;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.PMMessage;
import com.example.productmanagement.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management")
@Validated
public class ProductController {

	  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> create(@Valid @RequestBody Product product) {
    	   logger.info("Received request to create product: {}", product.getName());
    	return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Response> getById(@PathVariable String id) {
    	logger.info("Received request to getProduct By Id :"+id);
        return ResponseEntity.ok(new Response(PMMessage.SUCCESS,productService.getProductById(id)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Response> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {
    	logger.info("Received request to get all product.");
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return ResponseEntity.ok( new Response(PMMessage.SUCCESS ,productService.getAllProducts(pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> update(@PathVariable String id, @Valid @RequestBody Product product) {
    	logger.info("Received request to update product with id: {}", id);
    	return ResponseEntity.ok(new Response(PMMessage.SUCCESS, productService.updateProduct(id, product)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
    	 logger.info("Received request to delete product with id: {}", id);
    	productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}