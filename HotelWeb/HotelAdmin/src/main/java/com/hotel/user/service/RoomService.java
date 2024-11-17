package com.hotel.user.service;

import com.hotel.common.entity.Room;
import com.hotel.user.model.dto.reponse.RoomResponse;
import com.hotel.user.model.dto.request.RoomRequest;

public interface RoomService {
    RoomResponse createRoom(Long hotelId, RoomRequest roomRequest);
    RoomResponse getRoom(Long id);
}
