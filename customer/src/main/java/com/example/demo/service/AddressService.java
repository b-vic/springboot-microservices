package com.example.demo.service;

import com.example.demo.dto.AddressDto;
import com.example.demo.entity.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDto> findCustomerAddresses() {
        return addressRepository.findAll().stream().map(address -> new AddressDto(address.getId(), address.getStreet(), address.getCity())).toList();
    }

    public AddressDto findAddress(Long addressId) {
        return AddressDto.fromEntity(addressRepository.findById(addressId).orElseThrow());
    }

    public AddressDto updateAddress(AddressDto addressDto) {
        Address address = addressRepository.findById(addressDto.getAddressId()).orElseThrow();
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        return AddressDto.fromEntity(addressRepository.save(address));
    }
}
