package com.hotel.customer.repository;

import com.hotel.common.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, CrudRepository<Hotel, Long> {
    Page<Hotel> findByUserId(Long userId, Pageable pageable);
    @Query("SELECT h FROM Hotel h " +
            "JOIN h.rooms r " +
            "WHERE (:checkin IS NULL OR :checkout IS NULL OR r.id NOT IN (" +
            "   SELECT br.room.id FROM Booked_room br " +
            "   WHERE br.checkinDate < :checkout AND br.checkoutDate > :checkin" +
            ")) " +
            "AND (:keyword IS NULL OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "     OR LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "     OR LOWER(h.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Hotel> findHotelsWithAvailableRoomsAndKeyword(@Param("checkin") Date checkin,
                                                       @Param("checkout") Date checkout,
                                                       @Param("keyword") String keyword,
                                                       Pageable pageable);


}
