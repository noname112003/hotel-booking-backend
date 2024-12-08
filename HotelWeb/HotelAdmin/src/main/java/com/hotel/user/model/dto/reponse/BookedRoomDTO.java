package com.hotel.user.model.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedRoomDTO {
    private Long id;
    private String hotelName;
    private String roomName; // Tên phòng
    private String customerName; // Tên khách hàng
    private Date checkinDate;
    private Date checkoutDate;
    private Date bookingDate;
    private BigDecimal totalCost;
}