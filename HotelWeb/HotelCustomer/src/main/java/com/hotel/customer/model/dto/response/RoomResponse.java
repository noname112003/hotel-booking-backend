package com.hotel.customer.model.dto.response;

import com.hotel.common.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoomResponse {
    private Long id;
    private Long number;
    private String roomType;
    private String description;
    private double price;
    private Long hotelId; // Nếu cần trả về thông tin khách sạn
    private List<String> imagePaths; // Giả sử bạn muốn trả về các đường dẫn của ảnh

    // Phương thức chuyển đổi từ Room entity sang RoomResponse DTO
    public RoomResponse convertToDTO(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .number(room.getNumber())
                .roomType(room.getRoomType())
                .description(room.getDescription())
                .price(room.getPrice())
                .hotelId(room.getHotel().getId())
                .imagePaths(room.getImages().stream()
                        .map(image -> image.getPath())
                        .toList())
                .build();
    }
}