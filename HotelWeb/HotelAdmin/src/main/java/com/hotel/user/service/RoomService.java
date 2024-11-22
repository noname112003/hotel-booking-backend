package com.hotel.user.service;

import com.hotel.common.entity.Room;
import com.hotel.user.model.dto.reponse.RoomResponse;
import com.hotel.user.model.dto.request.RoomRequest;

import java.util.List;

public interface RoomService {
    RoomResponse createRoom(Long hotelId, RoomRequest roomRequest);
    RoomResponse getRoom(Long id);
    List<RoomResponse> getRoomsByHotelId(Long hotelId);
    RoomResponse updateRoom(Long roomId, RoomRequest roomRequest);

}
