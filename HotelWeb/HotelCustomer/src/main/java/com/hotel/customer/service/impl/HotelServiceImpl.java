package com.hotel.customer.service.impl;


import com.hotel.common.entity.Hotel;
import com.hotel.customer.model.dto.response.HotelResponse;
import com.hotel.customer.repository.HotelRepository;
import com.hotel.customer.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> searchHotelsWithAvailableRooms(Date checkin, Date checkout, String keyword, Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findHotelsWithAvailableRoomsAndKeyword(checkin, checkout, keyword, pageable);

        // Chuyển đổi danh sách khách sạn từ Entity sang DTO
        return hotels.map(hotel -> new HotelResponse().convertToDTO(hotel));
    }
}
