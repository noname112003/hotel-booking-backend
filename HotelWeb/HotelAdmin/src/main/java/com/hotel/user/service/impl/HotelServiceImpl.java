package com.hotel.user.service.impl;

import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Image_hotel;
import com.hotel.common.entity.User;
import com.hotel.user.exception.HotelNotFoundException;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.request.HotelRequest;
import com.hotel.user.repository.HotelRepository;
import com.hotel.user.service.HotelService;
import com.hotel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private UserService userService;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public HotelResponse registerHotel(HotelRequest hotelRequest){
        String email = userService.getCurrentUserEmail();
        User user = userService.getUser(email);

        Hotel hotel = Hotel.builder()
                .name(hotelRequest.getName())
                .address(hotelRequest.getAddress())
                .description(hotelRequest.getDescription())
                .checkin(hotelRequest.getCheckin())
                .checkout(hotelRequest.getCheckout())
                .parking(hotelRequest.getParking())
                .keep_luggage(hotelRequest.getKeepLuggage())
                .free_wifi(hotelRequest.getFreeWifi())
                .laundry_service(hotelRequest.getLaundryService())
                .room_service(hotelRequest.getRoomService())
                .user(user)
                .build();

        if (hotelRequest.getPaths() != null) {
            List<Image_hotel> images = hotelRequest.getPaths().stream()
                    .map(path -> Image_hotel.builder()
                            .path(path)
                            .hotel(hotel) // Gắn hotel vào từng ảnh
                            .build())
                    .toList();
            hotel.setImages(images);
        }
        return new HotelResponse().convertToDTO(hotelRepository.save(hotel));
    }
    @Override
    public HotelResponse getHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with id " + id + " not found"));

        return new HotelResponse().convertToDTO(hotel);

    }
}
