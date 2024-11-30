package com.hotel.customer.controller;


import com.hotel.common.entity.Booked_room;
import com.hotel.customer.model.dto.request.BookRoomRequest;
import com.hotel.customer.service.BookedRoomService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@RestController
@RequestMapping("/api/booked-rooms")
@RequiredArgsConstructor
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;


    @PostMapping("/book")
    public ResponseEntity<?> bookRoom(@RequestBody BookRoomRequest request) {
        try {
            Booked_room bookedRoom = bookedRoomService.bookRoom(
                    request.getRoomId(),
                    request.getCustomerId(),
                    request.getCheckinDate(),
                    request.getCheckoutDate()
            );
            return ResponseEntity.ok(bookedRoom);
        } catch (MessagingException | UnsupportedEncodingException e) {
            // Xử lý ngoại lệ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi đặt phòng: " + e.getMessage());
        }
    }
}
