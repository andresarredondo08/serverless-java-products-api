package com.andres.serverless_java_products_api.service;

import com.andres.serverless_java_products_api.dto.CreateProductRequest;
import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.dto.UpdateProductRequest;
import com.andres.serverless_java_products_api.model.Product;
import com.andres.serverless_java_products_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.name());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(request.category());
        product.setCreatedAt(now);
        product.setUpdatedAt(now);


        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    public ProductResponse getById(String id){
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

            return mapToResponse(product);
    }

    public List<ProductResponse> getAll(){
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse update(UpdateProductRequest request){
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id:" + request.id()));

        if (request.name() != null && !request.name().isBlank()) {
            product.setName(request.name());
        }

        if (request.description() != null) {
            product.setDescription(request.description());
        }

        if (request.price() != null) {
            if (request.price().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Product price must be greater than zero");
            }
            product.setPrice(request.price());
        }

        if (request.stock() != null) {
            if (request.stock() < 0) {
                throw new IllegalArgumentException("Product stock cannot be negative");
            }
            product.setStock(request.stock());
        }

        if (request.category() != null && !request.category().isBlank()) {
            product.setCategory(request.category());
        }

        product.setUpdatedAt(Instant.now());

        return mapToResponse(productRepository.save(product));
    }

    public String delete(String id){
        productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        productRepository.delete(id);

        return "Product deleted successfully";
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
