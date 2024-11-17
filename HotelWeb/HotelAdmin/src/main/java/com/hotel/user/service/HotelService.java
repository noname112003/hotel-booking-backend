package com.hotel.user.service;

import com.hotel.common.entity.Hotel;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.request.HotelRequest;

public interface HotelService {
    HotelResponse registerHotel(HotelRequest hotelRequest);
    HotelResponse getHotel(Long id);

}
