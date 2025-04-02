package com.example.demo.service;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
        this.customerRepository = customerRepository;
        this.restTemplate = restTemplate;
    }

    public CustomerDto getCustomer(String customerId) {
        Optional<Customer> customer = customerRepository.findByExtCustomerId(customerId);
        return CustomerDto.fromEntity(customer.orElseThrow());
    }

    public CustomerDto getCustomerProduct(String customerId) {
        Optional<Customer> customer = customerRepository.findByExtCustomerId(customerId);
        CustomerDto customerDto = CustomerDto.fromEntity(customer.orElseThrow());

        //Get all the customers product details:
        Set<String> customersProducts = customer.get().getProducts(); //cust prod relationship
        Set<Map<String, Object>> productDetails = restTemplate.getForObject("http://PRODUCT-SERVICE/product/{products}", Set.class, customersProducts);
        customerDto.setProducts(productDetails);

        return customerDto;
    }

    public List<CustomerDto> getCustomers() {
        return customerRepository.findAll().stream().map(CustomerDto::fromEntity).toList();
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {

        Customer customerNew = new Customer(customerDto.getCustomerId()
                , customerDto.getFirstName()
                , customerDto.getLastName());
        //customerNew.setAddresses(AddressDto.toEntity(customerDto.getAddressDto()));
        customerNew.addAddresses(AddressDto.toEntity(customerDto.getAddressDto()));
        customerNew.setProducts(customerDto.getProducts().stream().map(p -> p.get("sku").toString()).collect(Collectors.toSet()));
        return saveCustomer(customerNew);
    }

    public Optional<List<CustomerDto>> getCustomerByName(String firstName, String lastName) {
        Optional<List<Customer>> customers = customerRepository.findByFirstNameAndLastName(firstName, lastName);
        return customers.map(customerList -> customerList.stream().map(CustomerDto::fromEntity).toList());
    }

    @Transactional
    public CustomerDto saveCustomer(Customer customer) {
        Customer c = customerRepository.saveAndFlush(customer);
        return CustomerDto.fromEntity(c);
    }

    @Transactional
    public Optional<CustomerDto> updateCustomer(CustomerDto customerDto) {
        Optional<Customer> custSearch = customerRepository.findByExtCustomerId(customerDto.getCustomerId());
        if (custSearch.isPresent()) {
            Set<AddressDto> addressDto = customerDto.getAddressDto();
            if (addressDto != null) {
                custSearch.get().addAddresses(AddressDto.toEntity(addressDto));
            }
            Set<Map<String, Object>> productDto = customerDto.getProducts();
            if (productDto != null) {
                custSearch.get().addProducts(customerDto.getProducts().stream().map(p -> p.get("sku").toString()).collect(Collectors.toSet()));
            }
            Customer c = customerRepository.save(custSearch.get());
            return Optional.of(CustomerDto.fromEntity(c));
        }
        //TODO: handle update customer doesn't exist
        return Optional.empty();
    }
}
