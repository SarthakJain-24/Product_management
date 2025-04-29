package com.example.productmanagement.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.productmanagement.bean.Response;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.PMMessage;
import com.example.productmanagement.exception.ProductException;
import com.example.productmanagement.repository.ProductRepository;
import com.example.productmanagement.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Response createProduct(Product product) {
        logger.info("Creating a new product: {}", product.getName());
		Optional<Product> existing = null;
		if (Objects.nonNull(product.getId())) {
			existing = productRepository.findById(product.getId().toString());
		}
		if (Objects.nonNull(existing) && !existing.isEmpty()) {
			return new Response(PMMessage.DUPLICATE_ID, existing);
		}
		return new Response(PMMessage.SUCCESS, productRepository.save(product));
	}

	@Override
	public Product getProductById(String id) {
		logger.info("Getting product with id: {}", id);
		return productRepository.findById(id).orElseThrow(() -> new ProductException(PMMessage.DATA_NOT_FOUND));
	}

	@Override
	public Page<Product> getAllProducts(Pageable pageable) {
		logger.info("Fetching all products");
		return productRepository.findAll(pageable);
	}

	@Override
	public Product updateProduct(String id, Product product) {
		logger.info("Updating product with id: {}", id);
		if (Objects.nonNull(product.getId())) {
			if (!id.equals(product.getId())) {
				throw new ProductException(PMMessage.ID_DIFFERENT);
			}
		}
		Product existing = getProductById(id);
		existing.setName(product.getName());
		existing.setDescription(product.getDescription());
		existing.setPrice(product.getPrice());
		existing.setQuantity(product.getQuantity());
		return productRepository.save(existing);
	}

	@Override
	public void deleteProduct(String id) {
		logger.info("Deleting product with id: {}", id);
		Product existing = getProductById(id);
		productRepository.delete(existing);
	}
}