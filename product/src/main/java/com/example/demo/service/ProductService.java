package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Optional<List<Product>> products = productRepository.findAllBySkuIn(sku);
        if (products.isPresent()) {
            List<ProductDto> productDtos = new ArrayList<>();
            for (Product product : products.get()) {
                productDtos.add(new ProductDto(product.getId(), product.getSku(), product.getName(), product.getDescription()));
            }
            return productDtos;
        }
        return Collections.emptyList();
    }

    public ProductDto findBySku(String sku) {
        Optional<Product> product = productRepository.findBySku(sku);
        return ProductDto.fromEntity(product.orElseThrow());
    }

    public ProductDto save(ProductDto productDto) {
        Product product = ProductDto.toEntity(productDto);
        return ProductDto.fromEntity(productRepository.save(product));
    }

    public Optional<ProductDto> updateProduct(ProductDto productDto) {
        Optional<Product> productSearch = productRepository.findBySku(productDto.getSku());
        if (productSearch.isPresent()) { //sku should be unique
            productSearch.get().setName(productDto.getName());
            productSearch.get().setDescription(productDto.getDescription());
            return Optional.of(ProductDto.fromEntity(productRepository.save(productSearch.get())));
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteProduct(String sku) {
        Product productSearch = productRepository.findBySku(sku).orElseThrow();
        productRepository.delete(productSearch);
    }

}
