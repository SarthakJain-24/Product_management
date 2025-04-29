package com.example.productmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.productmanagement.bean.Response;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.PMMessage;
import com.example.productmanagement.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("1");
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);
        product.setQuantity(10);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createProduct_ShouldReturnCreatedProduct() {
        when(productService.createProduct(any(Product.class))).thenReturn(new Response (PMMessage.SUCCESS ,product));

        ResponseEntity<Response> response = productController.create(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody().getData());  
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getProductById_ShouldReturnProduct() {
        when(productService.getProductById("1")).thenReturn(product);

        ResponseEntity<Response> response = productController.getById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody().getData());  
        verify(productService, times(1)).getProductById("1");
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    void getAllProducts_ShouldReturnPagedProducts() {
        Page<Product> page = new PageImpl<>(Arrays.asList(product));
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Response> response = productController.getAll(0, 5, new String[]{"name", "asc"});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Page<Product> pageData = (Page<Product>) response.getBody().getData();  
        assertEquals(1, pageData.getContent().size());
        verify(productService, times(1)).getAllProducts(any(Pageable.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateProduct_ShouldReturnUpdatedProduct() {
        when(productService.updateProduct(eq("1"), any(Product.class))).thenReturn(product);

        ResponseEntity<Response> response = productController.update("1", product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody().getData());  
        verify(productService, times(1)).updateProduct(eq("1"), any(Product.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteProduct_ShouldReturnNoContent() {
        doNothing().when(productService).deleteProduct("1");

        ResponseEntity<Void> response = productController.delete("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct("1");
    }
}
