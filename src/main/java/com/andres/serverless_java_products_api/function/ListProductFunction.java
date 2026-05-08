package com.andres.serverless_java_products_api.function;

import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class ListProductFunction {

    @Bean
    public Function<Map<String, Object>, List<ProductResponse>> getAllProducts(ProductService productService){
        return event -> productService.getAll();
        }
    }

