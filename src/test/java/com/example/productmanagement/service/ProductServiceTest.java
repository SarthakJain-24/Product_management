package com.example.productmanagement.service;

import com.example.productmanagement.entity.Product;
import com.example.productmanagement.repository.ProductRepository;
import com.example.productmanagement.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;  // Use concrete implementation

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("1", "Product1", "Description", 100.0, 10);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(product);

        // Assert
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findById("1")).thenReturn(java.util.Optional.of(product));

        // Act
        Product foundProduct = productService.getProductById("1");

        // Assert
        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById("1");
    }
   
    
    @Test
    void deleteProduct_ShouldReturnSuccessMessage() {
        // Arrange
        // Mocking the repository methods
        when(productRepository.findById("1")).thenReturn(java.util.Optional.of(product));  // Mocking findById to return the product
        doNothing().when(productRepository).delete(any(Product.class));  // Mocking delete to do nothing

        // Act
        String response = productService.deleteProduct("1");

        // Assert
        assertEquals("1", response);  // Check that the ID is returned
        verify(productRepository, times(1)).findById("1");  // Verify findById was called
        verify(productRepository, times(1)).delete(any(Product.class));  // Verify delete was called
    }
}