package com.hotel.user.repository;

import com.hotel.common.entity.Image_hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageHotelRepository extends JpaRepository<Image_hotel, Long> {

}
