package com.andres.serverless_java_products_api.function;

import com.andres.serverless_java_products_api.dto.ProductResponse;
import com.andres.serverless_java_products_api.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Configuration
public class GetProductionFunction {

    @Bean
    public Function<Map<String, Object>, ProductResponse> getProduct(ProductService productService){
        return event -> {
            Map<String,String> pathParameters = (Map<String, String>) event.get("pathParameters");
            String id = pathParameters.get("id");
            return productService.getById(id);
        };
    }
}
