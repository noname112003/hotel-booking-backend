package com.hotel.user.repository;

import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, CrudRepository<Hotel, Long> {
    Page<Hotel> findByUserId(Long userId, Pageable pageable);
    // Truy vấn tìm kiếm theo userId và keyword
    @Query("SELECT h FROM Hotel h WHERE h.user.id = :userId AND " +
            "(LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(h.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Hotel> findByUserIdAndKeyword(@Param("userId") Long userId,
                                       @Param("keyword") String keyword,
                                       Pageable pageable);

}
