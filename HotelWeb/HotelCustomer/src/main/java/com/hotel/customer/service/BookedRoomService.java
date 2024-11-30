package com.hotel.customer.service;

import com.hotel.common.entity.Booked_room;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public interface BookedRoomService {
    Booked_room bookRoom(Long roomId, Long customerId, Date checkinDate, Date checkoutDate) throws MessagingException, UnsupportedEncodingException;
}
