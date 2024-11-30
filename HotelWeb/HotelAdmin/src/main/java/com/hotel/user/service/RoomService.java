package com.hotel.user.service;

import com.hotel.common.entity.Room;
import com.hotel.user.model.dto.reponse.RoomResponse;
import com.hotel.user.model.dto.request.RoomRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(Long hotelId, RoomRequest roomRequest);
    RoomResponse getRoom(Long id);
    Page<RoomResponse> getRoomsByHotelIdAndKeyword(Long hotelId, String keyword, Pageable pageable);
    RoomResponse updateRoom(Long roomId, RoomRequest roomRequest);

}
