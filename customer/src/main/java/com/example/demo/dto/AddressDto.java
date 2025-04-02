package com.example.demo.dto;

import com.example.demo.entity.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotBlank;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressDto {

    private Long addressId;

    @NotBlank(message = "Street name is mandatory")
    private String city;

    @NotBlank(message = "City is mandatory")
    private String street;

    @JsonBackReference //avoid recursive loop
    private CustomerDto customerDto;

    public AddressDto() {
    }

    public AddressDto(Long id, String street, String city) {
        this.addressId = id;
        this.city = city;
        this.street = street;
    }

    public static AddressDto fromEntity(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(address.getId());
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        return addressDto;
    }

    public static Set<AddressDto> fromEntity(Set<Address> address) {
        return address.stream().map(a -> new AddressDto(a.getId(), a.getStreet(), a.getCity())).collect(Collectors.toSet());
    }

    public static Set<Address> toEntity(Set<AddressDto> addressDto) {
        if (addressDto == null) {
            return Collections.emptySet();
        }
        return addressDto.stream().map(a -> new Address(a.getAddressId(), a.getStreet(), a.getCity())).collect(Collectors.toSet());
    }

    public static Address toEntity(AddressDto addressDto) {
        return new Address(addressDto.getAddressId(), addressDto.getStreet(), addressDto.getCity());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

}
