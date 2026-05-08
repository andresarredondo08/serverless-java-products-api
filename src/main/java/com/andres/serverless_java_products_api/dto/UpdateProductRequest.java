package com.andres.serverless_java_products_api.dto;

import java.math.BigDecimal;

public record UpdateProductRequest (
        String id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
){
}
