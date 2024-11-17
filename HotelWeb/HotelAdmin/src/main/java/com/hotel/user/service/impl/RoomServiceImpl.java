package com.hotel.user.service.impl;


import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Room;
import com.hotel.user.exception.RoomNotFoundException;
import com.hotel.user.model.dto.reponse.RoomResponse;
import com.hotel.user.model.dto.request.RoomRequest;
import com.hotel.user.repository.HotelRepository;
import com.hotel.user.repository.RoomRepository;
import com.hotel.user.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public RoomResponse createRoom(Long hotelId, RoomRequest roomRequest){
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalStateException("Hotel with id " + hotelId + " not found"));
        Room room = Room.builder()
                .roomType(roomRequest.getRoomType())
                .description(roomRequest.getDescription())
                .price(roomRequest.getPrice())
                .hotel(hotel)
                .build();

        return new RoomResponse().convertToDTO(roomRepository.save(room));
    }
    @Override
    public RoomResponse getRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with id " + id + " not found"));

        return new RoomResponse().convertToDTO(room); // Chuyển đổi Room entity thành RoomResponse DTO
    }
}
