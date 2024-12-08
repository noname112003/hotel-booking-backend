package com.hotel.user.repository;


import com.hotel.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Query("select u from User u where u.email = :email")
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User c SET c.name = :name, c.phoneNumber = :phoneNumber WHERE c.email = :email")
    void updateAdminByEmail(String name, String phoneNumber, String email);
}
