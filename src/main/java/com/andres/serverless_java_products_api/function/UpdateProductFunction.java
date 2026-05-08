package com.andres.serverless_java_products_api.function;

import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.dto.UpdateProductRequest;
import com.andres.serverless_java_products_api.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class UpdateProductFunction {

    @Bean
    public Function<UpdateProductRequest, ProductResponse> updateProduct(ProductService productService){
        return productService::update;
    }
}
