package com.hotel.user.service;

import com.hotel.common.entity.Hotel;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.request.HotelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    HotelResponse registerHotel(HotelRequest hotelRequest);
    HotelResponse getHotel(Long id);
    Page<HotelResponse> getAllHotels_v2(Long userId, String keyword, Pageable pageable);
    HotelResponse updateHotel(Long hotelId, HotelRequest hotelRequest);
    void deleteRoomFromHotel(Long hotelId, Long roomId);
    List<HotelResponse> getAllHotels(Long userId);
}
