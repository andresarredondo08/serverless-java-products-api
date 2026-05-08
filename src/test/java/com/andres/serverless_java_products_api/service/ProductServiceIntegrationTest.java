package com.andres.serverless_java_products_api.service;

import com.andres.serverless_java_products_api.dto.CreateProductRequest;
import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.dto.UpdateProductRequest;
import com.andres.serverless_java_products_api.model.Product;
import com.andres.serverless_java_products_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    void shouldCreateGetUpdateListAndDeleteProduct() {
        CreateProductRequest createRequest = new CreateProductRequest(
                "Laptop",
                "Gaming laptop",
                BigDecimal.valueOf(1500),
                10,
                "Electronics"
        );

        ProductResponse created = productService.create(createRequest);

        assertNotNull(created.id());
        assertEquals("Laptop", created.name());

        ProductResponse found = productService.getById(created.id());

        assertEquals(created.id(), found.id());
        assertEquals("Laptop", found.name());

        UpdateProductRequest updateRequest = new UpdateProductRequest(
                created.id(),
                "Laptop Pro",
                "Updated laptop",
                BigDecimal.valueOf(2000),
                5,
                "Electronics"
        );

        ProductResponse updated = productService.update(updateRequest);

        assertEquals("Laptop Pro", updated.name());
        assertEquals(BigDecimal.valueOf(2000), updated.price());
        assertEquals(5, updated.stock());

        List<ProductResponse> products = productService.getAll();

        assertFalse(products.isEmpty());

        String deleteResponse = productService.delete(created.id());

        assertEquals("Product deleted successfully", deleteResponse);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.getById(created.id())
        );

        assertEquals("Product not found with id: " + created.id(), exception.getMessage());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        ProductRepository productRepository() {
            return new InMemoryProductRepository();
        }
    }

    static class InMemoryProductRepository extends ProductRepository {

        private final Map<String, Product> database = new HashMap<>();

        InMemoryProductRepository() {
            super(null);
        }

        @Override
        public Product save(Product product) {
            database.put(product.getId(), product);
            return product;
        }

        @Override
        public Optional<Product> findById(String id) {
            return Optional.ofNullable(database.get(id));
        }

        @Override
        public List<Product> findAll() {
            return new ArrayList<>(database.values());
        }

        @Override
        public void delete(String id) {
            database.remove(id);
        }
    }
}
