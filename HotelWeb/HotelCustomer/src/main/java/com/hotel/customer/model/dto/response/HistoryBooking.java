package com.hotel.customer.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryBooking {
    private Long id;
    private String nameHotel;
    private Long number;
    private String roomType;
    private Date checkinDate;
    private Date checkoutDate;
    private Date bookingDate;
    private BigDecimal totalCost;

}
