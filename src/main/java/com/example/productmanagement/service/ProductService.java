package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(String id);
    Page<Product> getAllProducts(Pageable pageable);
    Product updateProduct(String id, Product product);
    String deleteProduct(String id);
}