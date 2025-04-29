package com.example.productmanagement.service;

import com.example.productmanagement.bean.Response;
import com.example.productmanagement.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ProductService {
    Response createProduct(Product product);
    Product getProductById(String id);
    Page<Product> getAllProducts(Pageable pageable);
    Product updateProduct(String id, Product product);
    void deleteProduct(String id);
}