package com.hotel.user.repository;

import com.hotel.common.entity.Image_room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRoomRepository extends JpaRepository<Image_room, Long> {

}
