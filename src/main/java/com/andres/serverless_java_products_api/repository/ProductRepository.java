package com.andres.serverless_java_products_api.repository;

import com.andres.serverless_java_products_api.model.Product;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final DynamoDbTable<Product> productTable;

    public ProductRepository(DynamoDbTable<Product> productTable){
        this.productTable =  productTable;
    }

    public Product save(Product product){
        productTable.putItem(product);
        return product;
    }

    public Optional<Product> findById(String id){
        Product product =  productTable.getItem(
                Key.builder()
                        .partitionValue(id)
                        .build()
        );
        return Optional.ofNullable(product);
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        productTable.scan()
                .items()
                .forEach(products::add);

        return products;
    }

    public void delete(String id){
        productTable.deleteItem(
                Key.builder()
                        .partitionValue(id)
                        .build()
        );
    }
}
