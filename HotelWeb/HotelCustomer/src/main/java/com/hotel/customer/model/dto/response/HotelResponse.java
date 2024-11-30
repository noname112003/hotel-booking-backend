package com.hotel.customer.model.dto.response;

import com.hotel.common.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
    private String checkin;
    private String checkout;
    private Boolean parking;
    private Boolean keepLuggage;
    private Boolean freeWifi;
    private Boolean laundryService;
    private Boolean roomService;
    private List<String> paths;

    public HotelResponse convertToDTO(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .description(hotel.getDescription())
                .checkin(hotel.getCheckin())
                .checkout(hotel.getCheckout())
                .parking(hotel.getParking())
                .keepLuggage(hotel.getKeep_luggage())
                .freeWifi(hotel.getFree_wifi())
                .laundryService(hotel.getLaundry_service())
                .roomService(hotel.getRoom_service())
                .paths(hotel.getImages() != null
                        ? hotel.getImages().stream()
                        .map(image -> image.getPath())
                        .toList()
                        : null)
                .build();
    }
}
