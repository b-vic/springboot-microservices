package com.example.demo.controller;

import com.example.demo.dto.CustomerDto;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("customer")
@CrossOrigin("*")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> customers = customerService.getCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    @ResponseBody
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String customerId) {
        CustomerDto customer = customerService.getCustomer(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/products")
    @ResponseBody
    public ResponseEntity<CustomerDto> getCustomerProducts(@PathVariable String customerId) {
        CustomerDto customer = customerService.getCustomerProduct(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<CustomerDto> createCustomerById(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/name")
    @ResponseBody
    public ResponseEntity<?> getByCustomerName(@RequestParam String fullName) {
        Optional<CustomerDto> customer = customerService.getCustomerByName(fullName);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Customer Found",HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto) {
        Optional<CustomerDto> customerDtoResponse = customerService.updateCustomer(customerDto);
        if (customerDtoResponse.isPresent()) {
            return new ResponseEntity<>(customerDtoResponse.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("No Customer Found", HttpStatus.NOT_FOUND);
    }
}
