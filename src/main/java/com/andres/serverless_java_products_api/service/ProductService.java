package com.andres.serverless_java_products_api.service;

import com.andres.serverless_java_products_api.dto.CreateProductRequest;
import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.model.Product;
import com.andres.serverless_java_products_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductResponse create(CreateProductRequest request){
        validateProduct(request);
        Instant now = Instant.now();
        Product product = new Product()
                .setId(UUID.randomUUID().toString())
                .setName(request.name())
                .setDescription(request.description())
                .setPrice(request.price())
                .setStock(request.stock())
                .setCategory(request.category())
                .setCreatedAt(now)
                .setUpdatedAt(now);

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }



    private void validateProduct(CreateProductRequest product) {

        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (product.price() == null || product.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        if (product.stock() == null || product.stock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }

        if (product.category() == null || product.category().isBlank()) {
            throw new IllegalArgumentException("Product category is required");
        }
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
