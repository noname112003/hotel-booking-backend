package com.hotel.user.service.impl;

import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Image_hotel;
import com.hotel.common.entity.Room;
import com.hotel.common.entity.User;
import com.hotel.user.exception.HotelNotFoundException;
import com.hotel.user.exception.RoomNotFoundException;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.request.HotelRequest;
import com.hotel.user.repository.HotelRepository;
import com.hotel.user.repository.RoomRepository;
import com.hotel.user.service.HotelService;
import com.hotel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private UserService userService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

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

    @Override
    public Page<HotelResponse> getAllHotels_v2(Long userId, String keyword, Pageable pageable) {
        Page<Hotel> hotels;
        if (keyword != null) {
            hotels = hotelRepository.findByUserIdAndKeyword(userId, keyword, pageable);
        } else {
            hotels = hotelRepository.findByUserId(userId, pageable);
        }
        if (hotels.isEmpty()) {
            throw new HotelNotFoundException("No hotels found for user with ID: " + userId);
        }

        return hotels.map(hotel -> new HotelResponse().convertToDTO(hotel));
    }
    @Override
    public HotelResponse updateHotel(Long hotelId, HotelRequest hotelRequest) {
        // Tìm hotel theo ID
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with ID: " + hotelId));

        // Cập nhật thông tin từ HotelRequest
        hotel.setName(hotelRequest.getName());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setDescription(hotelRequest.getDescription());
        hotel.setCheckin(hotelRequest.getCheckin());
        hotel.setCheckout(hotelRequest.getCheckout());
        hotel.setParking(hotelRequest.getParking());
        hotel.setKeep_luggage(hotelRequest.getKeepLuggage());
        hotel.setFree_wifi(hotelRequest.getFreeWifi());
        hotel.setLaundry_service(hotelRequest.getLaundryService());
        hotel.setRoom_service(hotelRequest.getRoomService());


        if (hotelRequest.getPaths() != null) {

            List<Image_hotel> newImages = hotelRequest.getPaths().stream()
                    .map(path -> Image_hotel.builder().path(path).hotel(hotel).build())
                    .toList();

            hotel.setImages(newImages);
        }

        // Lưu thông tin hotel sau khi cập nhật
        Hotel updatedHotel = hotelRepository.save(hotel);

        // Trả về HotelResponse
        return new HotelResponse().convertToDTO(updatedHotel);
    }
    @Override
    public void deleteRoomFromHotel(Long hotelId, Long roomId) {
        // Tìm khách sạn theo ID
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with ID: " + hotelId));

        // Tìm phòng trong khách sạn theo ID
        Room room = hotel.getRooms().stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));

        // Xóa phòng khỏi danh sách phòng của khách sạn
        hotel.getRooms().remove(room);

        // Xóa phòng khỏi cơ sở dữ liệu (nếu cần)
        roomRepository.delete(room);

        // Lưu lại khách sạn sau khi xóa phòng
        hotelRepository.save(hotel);
    }
}
