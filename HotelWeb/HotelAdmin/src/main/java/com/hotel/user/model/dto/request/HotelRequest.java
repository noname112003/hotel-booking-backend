package com.hotel.user.model.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelRequest {
    @NotEmpty(message = "Name is required")
    private String name;
    private String address;
    private String description;
    @NotNull(message = "Check-in date is required")
    private String checkin;
    @NotNull(message = "Check-out date is required")
    private String checkout;
    private Boolean parking;
    private Boolean keepLuggage;
    private Boolean freeWifi;
    private Boolean laundryService;
    private Boolean roomService;
    private List<String> paths;
}
