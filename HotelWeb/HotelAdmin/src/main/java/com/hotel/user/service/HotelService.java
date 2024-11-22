package com.hotel.user.service;

import com.hotel.common.entity.Hotel;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.request.HotelRequest;

import java.util.List;

public interface HotelService {
    HotelResponse registerHotel(HotelRequest hotelRequest);
    HotelResponse getHotel(Long id);
    List<HotelResponse> getAllHotels(Long userId);
    HotelResponse updateHotel(Long hotelId, HotelRequest hotelRequest);
    void deleteRoomFromHotel(Long hotelId, Long roomId);

}
