package com.andres.serverless_java_products_api.function;

import com.andres.serverless_java_products_api.dto.CreateProductRequest;
import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class CreateProductFunction {

    @Bean
    public Function<CreateProductRequest, ProductResponse> createProduct(ProductService productService){
        return productService::create;
    }
}
