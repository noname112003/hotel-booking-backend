package com.hotel.user.service.impl;


import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Image_hotel;
import com.hotel.common.entity.Image_room;
import com.hotel.common.entity.Room;
import com.hotel.user.exception.RoomNotFoundException;
import com.hotel.user.model.dto.reponse.RoomDTO;
import com.hotel.user.model.dto.reponse.RoomResponse;
import com.hotel.user.model.dto.request.RoomRequest;
import com.hotel.user.repository.HotelRepository;
import com.hotel.user.repository.ImageRoomRepository;
import com.hotel.user.repository.RoomRepository;
import com.hotel.user.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ImageRoomRepository imageRoomRepository;

    @Override
    public RoomResponse createRoom(Long hotelId, RoomRequest roomRequest){
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalStateException("Hotel with id " + hotelId + " not found"));
        Room room = Room.builder()
                .number(roomRequest.getNumber())
                .roomType(roomRequest.getRoomType())
                .description(roomRequest.getDescription())
                .price(roomRequest.getPrice())
                .hotel(hotel)
                .build();
        if (roomRequest.getPaths() != null) {
            List<Image_room> images = roomRequest.getPaths().stream()
                    .map(path -> Image_room.builder()
                            .path(path)
                            .room(room) // Gắn hotel vào từng ảnh
                            .build())
                    .toList();
            room.setImages(images);
        }
        return new RoomResponse().convertToDTO(roomRepository.save(room));
    }
    @Override
    public RoomResponse getRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with id " + id + " not found"));

        return new RoomResponse().convertToDTO(room); // Chuyển đổi Room entity thành RoomResponse DTO
    }
    @Override
    public Page<RoomDTO> getRoomsByHotelIdAndKeyword(List<Long> hotelIds, String keyword, Pageable pageable) {
        Page<Room> rooms;
        if (keyword != null || keyword.length() > 0) {
            rooms = roomRepository.findByHotelIdAndKeyword(hotelIds, keyword, pageable);
        } else {
            rooms = roomRepository.findByHotelIdIn(hotelIds, pageable);
        }
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException("No rooms found for hotel with ID: " );
        }
        return rooms.map(room -> new RoomDTO().convertToDTO(room));
    }
    @Override
    public RoomResponse updateRoom(Long roomId, RoomRequest roomRequest) {
        // Tìm phòng theo ID
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));

        // Cập nhật thông tin từ RoomRequest
        room.setNumber(roomRequest.getNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setDescription(roomRequest.getDescription());
        room.setPrice(roomRequest.getPrice());

        // Cập nhật danh sách hình ảnh nếu có
        if (roomRequest.getPaths() != null) {
            imageRoomRepository.deleteAllInBatch(room.getImages());

            // Tạo danh sách mới các hình ảnh từ các đường dẫn trong roomRequest
            List<Image_room> newImages = roomRequest.getPaths().stream()
                    .map(path -> Image_room.builder().path(path).room(room).build())
                    .toList();

            room.getImages().addAll(newImages); // Cập nhật danh sách hình ảnh mới
        }

        // Lưu thông tin phòng sau khi cập nhật
        Room updatedRoom = roomRepository.save(room);

        // Trả về RoomResponse
        return new RoomResponse().convertToDTO(updatedRoom);
    }

    @Override
    public Page<Room> getRoomsByHotelIds(List<Long> hotelIds, Pageable pageable) {
        return roomRepository.findByHotelIdIn(hotelIds, pageable);
    }

    @Override
    public List<RoomResponse> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId).stream()
                .map(room -> new RoomResponse().convertToDTO(room))
                .collect(Collectors.toList());
    }

}
