package com.example.demo.dto;

import com.example.demo.entity.Customer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;
import java.util.Set;

public class CustomerDto {

    public static CustomerDto fromEntity(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getExtCustomerId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setAddressDto(AddressDto.fromEntity(customer.getAddresses()));
        return customerDto;
    }

    public static Customer toEntity(CustomerDto customerDto, Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setExtCustomerId(customerDto.getCustomerId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        //customer.setAddresses(AddressDto.toEntity(customerDto.getAddressDto()));
        customer.addAddresses(AddressDto.toEntity(customerDto.getAddressDto()));
        return customer;
    }

    private String customerId;

    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    private Set<Map<String,Object>> products;

    @JsonManagedReference //avoid recursive loop
    @JsonProperty("address")
    private Set<AddressDto> addressDto;

    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<AddressDto> getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(Set<AddressDto> addressDto) {
        this.addressDto = addressDto;
    }

    public Set<Map<String, Object>> getProducts() {
        return products;
    }

    public void setProducts(Set<Map<String, Object>> products) {
        this.products = products;
    }

}
