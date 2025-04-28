package com.example.productmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.productmanagement.entity.Product;

@DataMongoTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("1", "Product1", "Description", 100.0, 10);
    }

    @Test
    void saveProduct_ShouldReturnSavedProduct() {
        // Arrange & Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertNotNull(savedProduct.getId());
        assertEquals(product.getId(), savedProduct.getId());
    }

    
    
    
    @Test
    void findByName_ShouldReturnProduct() {
        // Arrange
        productRepository.save(product);

        // Act
        Product foundProduct = productRepository.findById("1").orElse(null);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    void deleteProductByName_ShouldDeleteProduct() {
        // Arrange
        productRepository.save(product);

        // Act
        productRepository.deleteById("1");

        // Assert
        assertFalse(productRepository.findById("1").isPresent());
    }
}
