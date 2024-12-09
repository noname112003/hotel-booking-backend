package com.hotel.customer.service;

import com.hotel.common.entity.Booked_room;
import com.hotel.customer.model.dto.response.HistoryBooking;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public interface BookedRoomService {
    Booked_room bookRoom(Long roomId, Long customerId, Date checkinDate, Date checkoutDate) throws MessagingException, UnsupportedEncodingException;
    Page<HistoryBooking> getHistoryByUserId(Long userId, Pageable pageable);
}
