package com.hotel.user.repository;

import com.hotel.common.entity.Booked_room;
import com.hotel.common.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookedRoomRepository extends JpaRepository<Booked_room, Long>, CrudRepository<Booked_room, Long> {
    @Query("""
    SELECT b FROM Booked_room b
    JOIN b.room r
    JOIN r.hotel h
    JOIN h.user u
    WHERE u.email = :adminEmail AND b.bookingDate = :today
""")
    Page<Booked_room> findByAdminEmailAndBookingDate(@Param("adminEmail") String adminEmail,
                                                     @Param("today") Date today,
                                                     Pageable pageable);

    @Query("""
    SELECT b FROM Booked_room b
    JOIN b.room r
    JOIN r.hotel h
    JOIN h.user u
    WHERE u.email = :adminEmail
""")
    Page<Booked_room> findByAdminEmail(@Param("adminEmail") String adminEmail, Pageable pageable);

    @Query("""
    SELECT b FROM Booked_room b
    JOIN b.room r
    JOIN r.hotel h
    JOIN h.user u
    WHERE u.email = :adminEmail AND b.checkoutDate <= :today
""")
    List<Booked_room> findByBeforeBaseDate(@Param("adminEmail") String adminEmail,
                                           @Param("today") Date today);
}
