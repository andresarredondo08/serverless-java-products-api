package com.andres.serverless_java_products_api.service;

import com.andres.serverless_java_products_api.dto.CreateProductRequest;
import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.dto.UpdateProductRequest;
import com.andres.serverless_java_products_api.model.Product;
import com.andres.serverless_java_products_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProductSuccessfully() {
        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                BigDecimal.valueOf(1500),
                10,
                "Electronics"
        );

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.create(request);

        assertNotNull(response.id());
        assertEquals("Laptop", response.name());
        assertEquals(BigDecimal.valueOf(1500), response.price());
        assertEquals(10, response.stock());
        assertEquals("Electronics", response.category());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNameIsMissing() {
        CreateProductRequest request = new CreateProductRequest(
                "",
                "Gaming laptop",
                BigDecimal.valueOf(1500),
                10,
                "Electronics"
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.create(request)
        );

        assertEquals("Product name is required", exception.getMessage());
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldGetProductByIdSuccessfully() {
        Product product = sampleProduct();

        when(productRepository.findById("123"))
                .thenReturn(Optional.of(product));

        ProductResponse response = productService.getById("123");

        assertEquals("123", response.id());
        assertEquals("Laptop", response.name());

        verify(productRepository).findById("123");
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById("not-found"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.getById("not-found")
        );

        assertEquals("Product not found with id: not-found", exception.getMessage());
        verify(productRepository).findById("not-found");
    }

    @Test
    void shouldListAllProducts() {
        Product product = sampleProduct();

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        List<ProductResponse> response = productService.getAll();

        assertEquals(1, response.size());
        assertEquals("Laptop", response.get(0).name());

        verify(productRepository).findAll();
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Product product = sampleProduct();

        UpdateProductRequest request = new UpdateProductRequest(
                "123",
                "Laptop Pro",
                null,
                BigDecimal.valueOf(2000),
                5,
                null
        );

        when(productRepository.findById("123"))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.update(request);

        assertEquals("123", response.id());
        assertEquals("Laptop Pro", response.name());
        assertEquals(BigDecimal.valueOf(2000), response.price());
        assertEquals(5, response.stock());

        verify(productRepository).findById("123");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        Product product = sampleProduct();

        when(productRepository.findById("123"))
                .thenReturn(Optional.of(product));

        String response = productService.delete("123");

        assertEquals("Product deleted successfully", response);

        verify(productRepository).findById("123");
        verify(productRepository).delete("123");
    }

    private Product sampleProduct() {
        Product product = new Product();
        product.setId("123");
        product.setName("Laptop");
        product.setDescription("Gaming laptop");
        product.setPrice(BigDecimal.valueOf(1500));
        product.setStock(10);
        product.setCategory("Electronics");
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return product;
    }
}