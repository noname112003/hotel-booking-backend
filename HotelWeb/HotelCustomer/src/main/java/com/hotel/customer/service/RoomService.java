package com.hotel.customer.service;


import com.hotel.common.entity.Room;
import com.hotel.customer.model.dto.response.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface RoomService {
    Page<RoomResponse> getAvailableRooms(Long hotelId, Date checkin, Date checkout, Pageable pageable);

}
