package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByExtCustomerId(String extCustomerId);

    Optional<List<Customer>> findByFirstNameAndLastName(String firstName, String lastName);

}
