package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByExtCustomerId(String extCustomerId);

    @Query(value = "select * from customer c where concat(c.firstName, c.lastName) = :fullName", nativeQuery = true)
    Optional<Customer> findByFullName(@Param("fullName") String fullName);

}
