package com.hotel.customer.repository;

import com.hotel.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Query("select u from Customer u where u.email = :email")
    Customer findByEmail(String email);
}
