package com.hotel.customer.repository;

import com.hotel.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Query("select u from Customer u where u.email = :email")
    Customer findByEmail(String email);

    @Query("select u from Customer u where u.id = :id")
    Customer findById(String id);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.name = :name, c.phoneNumber = :phoneNumber, c.email = :email, c.identification = :identification WHERE c.email = :email")
    void updateCustomerByEmail(String name, String phoneNumber, String email, String identification);
}
