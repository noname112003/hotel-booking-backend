package com.hotel.user.repository;

import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, CrudRepository<Room, Long> {
}
