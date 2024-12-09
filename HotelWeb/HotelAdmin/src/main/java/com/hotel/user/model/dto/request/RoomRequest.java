package com.hotel.user.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoomRequest {
    private Long number;
    private String roomType;
    private String description;
    private BigDecimal price;
    private List<String> paths;
}
