package com.hotel.customer.repository;

import com.hotel.common.entity.Booked_room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookedRoomRepository extends JpaRepository<Booked_room, Long> {
    @Query("SELECT b FROM Booked_room b WHERE b.room.id = :roomId AND " +
            "((:checkinDate BETWEEN b.checkinDate AND b.checkoutDate) OR " +
            "(:checkoutDate BETWEEN b.checkinDate AND b.checkoutDate) OR " +
            "(b.checkinDate BETWEEN :checkinDate AND :checkoutDate))")
    List<Booked_room> findConflictingBookings(@Param("roomId") Long roomId,
                                              @Param("checkinDate") Date checkinDate,
                                              @Param("checkoutDate") Date checkoutDate);

    @Query("SELECT br FROM Booked_room br WHERE br.customer.id = :userId")
    Page<Booked_room> findByCustomerId(@Param("userId") Long userId, Pageable pageable);
}