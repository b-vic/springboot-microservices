package com.example.demo.dto;

import com.example.demo.entity.Product;
import jakarta.validation.constraints.NotBlank;

public class ProductDto {

    private Long productId;

    @NotBlank(message = "Product sku is mandatory")
    private String sku;
    @NotBlank(message = "Product name is mandatory")
    private String name;

    private String description;

    public ProductDto() {
    }

    public ProductDto(Long id, String sku, String name, String description) {
        this.productId = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
    }

    public static ProductDto fromEntity(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        productDto.setSku(product.getSku());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        return productDto;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setSku(productDto.getSku());
        product.setDescription(productDto.getDescription());
        return product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
