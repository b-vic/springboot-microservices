package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
@CrossOrigin("*")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/page")
    @ResponseBody
    public ResponseEntity<List<ProductDto>> getProductsByPage(@RequestParam int pageNumber, @RequestParam int pageSize) {
        List<ProductDto> products = productService.findAllPageable(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productSku}")
    @ResponseBody
    public ResponseEntity<List<ProductDto>> getProductBySku(@PathVariable List<String> productSku) {
        List<ProductDto> products = productService.findBySku(productSku.stream().map(s -> s.replaceAll("[\\[\\]]", "")).toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto productDtoResponse = productService.save(productDto);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }
}
