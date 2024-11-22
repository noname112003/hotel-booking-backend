package com.hotel.user.repository;

import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, CrudRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId);
}
