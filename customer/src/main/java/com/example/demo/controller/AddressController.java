package com.example.demo.controller;

import com.example.demo.dto.AddressDto;
import com.example.demo.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
@CrossOrigin("*")
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AddressDto>> getAddresses() {
        List<AddressDto> customers = addressService.findCustomerAddresses();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    @ResponseBody
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId) {
        AddressDto addressDto = addressService.findAddress(addressId);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<AddressDto> updateAddressById(@RequestBody AddressDto addressDto) {
        AddressDto addressDtoResponse = addressService.updateAddress(addressDto);
        return new ResponseEntity<>(addressDtoResponse, HttpStatus.OK);
    }

}
