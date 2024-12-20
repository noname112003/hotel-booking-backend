package com.hotel.customer.repository;

import com.hotel.common.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, CrudRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND " +
            "(LOWER(r.roomType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Room> findByHotelIdAndKeyword(@Param("hotelId") Long hotelId,
                                       @Param("keyword") String keyword,
                                       Pageable pageable);
    Page<Room> findByHotelId(Long hotelId, Pageable pageable);
    @Query("SELECT r FROM Room r " +
            "WHERE r.hotel.id = :hotelId " +
            "AND r.id NOT IN (" +
            "   SELECT br.room.id FROM Booked_room br " +
            "   WHERE br.checkinDate < :checkout AND br.checkoutDate > :checkin" +
            ")")
    Page<Room> findAvailableRoomsByHotelAndDate(@Param("hotelId") Long hotelId,
                                                @Param("checkin") Date checkin,
                                                @Param("checkout") Date checkout,
                                                Pageable pageable);
}
