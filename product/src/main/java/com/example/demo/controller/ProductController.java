package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("product")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

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

    @GetMapping("/search/{productSku}")
    @ResponseBody
    public ResponseEntity<List<ProductDto>> getProductBySkus(@PathVariable List<String> productSku) {
        List<ProductDto> products = productService.findBySku(productSku.stream().map(s -> s.replaceAll("[\\[\\]]", "")).toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productSku}")
    @ResponseBody
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable String productSku) {
        ProductDto product = productService.findBySku(productSku);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto productDtoResponse = productService.save(productDto);
        return new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto) {
        Optional<ProductDto> productDtoResponse = productService.updateProduct(productDto);
        if (productDtoResponse.isPresent()) {
            return new ResponseEntity<>(productDtoResponse.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No Product Found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{productSku}")
    @ResponseBody
    public ResponseEntity<?> deleteProduct(@PathVariable String productSku) {
        productService.deleteProduct(productSku);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
