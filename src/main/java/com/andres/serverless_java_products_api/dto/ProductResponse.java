package com.andres.serverless_java_products_api.dto;

import org.springframework.beans.factory.aot.InstanceSupplierCodeGenerator;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        Instant createdAt,
        Instant updatedAt
) {
}
