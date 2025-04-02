package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductDto(product.getId(), product.getSku(), product.getName(), product.getDescription())).toList();
    }

    public List<ProductDto> findAllPageable(int pageNumber, int pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return products.stream().map(product -> new ProductDto(product.getId(), product.getSku(), product.getName(), product.getDescription())).toList();
    }

    public List<ProductDto> findBySku(List<String> sku) {
        List<Product> products = productRepository.findAllBySkuIn(sku).orElseThrow();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(new ProductDto(product.getId(), product.getSku(), product.getName(), product.getDescription()));
        }
        return productDtos;
    }

    public ProductDto save(ProductDto productDto) {
        Product product = ProductDto.toEntity(productDto);
        return ProductDto.fromEntity(productRepository.save(product));
    }
}
