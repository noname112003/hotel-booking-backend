package com.hotel.user.model.dto.reponse;

import com.hotel.common.entity.Room;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
public class RoomDTO {
    private Long id;
    private Long number;
    private String roomType;
    private String description;
    private double price;
    private Long hotelId;
    private String hotelName;
    private List<String> paths;

    public RoomDTO convertToDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .number(room.getNumber())
                .roomType(room.getRoomType())
                .description(room.getDescription())
                .price(room.getPrice())
                .hotelName(room.getHotel().getName())
                .hotelId(room.getHotel().getId())
                .paths(room.getImages().stream()
                        .map(image -> image.getPath())
                        .toList())
                .build();
    }
}
