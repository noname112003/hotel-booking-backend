package com.hotel.customer.service.impl;


import com.hotel.common.entity.Room;
import com.hotel.customer.model.dto.response.RoomResponse;
import com.hotel.customer.repository.RoomRepository;
import com.hotel.customer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    public Page<RoomResponse> getAvailableRooms(Long hotelId, Date checkin, Date checkout, Pageable pageable) {
        Page<Room> rooms = roomRepository.findAvailableRoomsByHotelAndDate(hotelId, checkin, checkout, pageable);
        return rooms.map(room -> new RoomResponse().convertToDTO(room));
    }



}
