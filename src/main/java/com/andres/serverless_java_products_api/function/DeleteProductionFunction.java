package com.andres.serverless_java_products_api.function;

import com.andres.serverless_java_products_api.repository.ProductRepository;
import com.andres.serverless_java_products_api.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class DeleteProductionFunction {

    @Bean
    public Function<Map<String,Object>, String> delete(ProductService productService){
        return event -> {
            Map<String, String> pathParameters = (Map<String, String>) event.get("pathParameters");
            String id = pathParameters.get("id");
            return productService.delete(id);
        };
    }
}
