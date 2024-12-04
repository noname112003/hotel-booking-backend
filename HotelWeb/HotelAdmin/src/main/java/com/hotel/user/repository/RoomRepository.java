package com.hotel.user.repository;

import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, CrudRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id IN :hotelIds AND " +
            "(LOWER(r.roomType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Room> findByHotelIdAndKeyword(List<Long> hotelIds,
                                       String keyword,
                                       Pageable pageable);
    Page<Room> findByHotelId(Long hotelId, Pageable pageable);

    @Query("SELECT r FROM Room r WHERE r.hotel.id IN :hotelIds")
    Page<Room> findByHotelIdIn(List<Long> hotelIds, Pageable pageable);

    List<Room> findByHotelId(Long hotelId);
}
