package com.hotel.customer.service;


import com.hotel.customer.model.dto.response.HotelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface HotelService {
    Page<HotelResponse> searchHotelsWithAvailableRooms(Date checkin, Date checkout, String keyword, Pageable pageable);

    Page<HotelResponse> searchDestinations(String keyword, Pageable pageable);
}
