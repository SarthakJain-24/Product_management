package com.example.productmanagement.service.impl;

import com.example.productmanagement.bean.Response;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.ProductException;
import com.example.productmanagement.repository.ProductRepository;
import com.example.productmanagement.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("1123", "Product 1", "Description", 100.0, 10);
    }

    @Test
    void createProduct_ShouldReturnSavedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Response savedProduct = productService.createProduct(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getData());
        Product productData = (Product) savedProduct.getData(); 
        assertEquals("Product 1", productData.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenExists() {
    	when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById("1123");

        assertNotNull(foundProduct);
        assertEquals("Product 1", foundProduct.getName());
        verify(productRepository, times(1)).findById("1123");
    }

    @Test
    void getProductById_ShouldThrowException_WhenNotFound() {
        when(productRepository.findById("1123")).thenReturn(Optional.empty());

        ProductException exception = assertThrows(ProductException.class, () -> {
            productService.getProductById("1123");
        });

        assertEquals("data not found with this id. ", exception.getMessage());
        verify(productRepository, times(1)).findById("1123");
    }

    @Test
    void getAllProducts_ShouldReturnPagedProducts() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Product> products = Arrays.asList(product);
        Page<Product> page = new PageImpl<>(products);

        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<Product> result = productService.getAllProducts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnProduct() {
        Product updatedProduct = new Product("1123", "Updated Name", "Updated Description", 200.0, 20);

        when(productRepository.findById("1123")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct("1123", updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(productRepository, times(1)).findById("1123");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void deleteProduct_ShouldDeleteProduct_WhenExists() {
        when(productRepository.findById("1123")).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteProduct("1123");

        verify(productRepository, times(1)).findById("1123");
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenNotFound() {
        when(productRepository.findById("1123")).thenReturn(Optional.empty());

        assertThrows(ProductException.class, () -> {
            productService.deleteProduct("1123");
        });

        verify(productRepository, times(1)).findById("1123");
        verify(productRepository, times(0)).delete(any(Product.class));
    }
}

