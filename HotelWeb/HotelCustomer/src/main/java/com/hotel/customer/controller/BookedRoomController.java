package com.hotel.customer.controller;


import com.hotel.common.entity.Booked_room;
import com.hotel.customer.model.dto.request.BookRoomRequest;
import com.hotel.customer.service.BookedRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping("/api/booked-rooms")
@RequiredArgsConstructor
public class BookedRoomController {
    private final BookedRoomService bookedRoomService;


    @PostMapping("/book")
    public ResponseEntity<Booked_room> bookRoom(@RequestBody BookRoomRequest request) {
        Booked_room bookedRoom = bookedRoomService.bookRoom(
                request.getRoomId(),
                request.getCustomerId(),
                request.getCheckinDate(),
                request.getCheckoutDate()
        );
        return ResponseEntity.ok(bookedRoom);
    }
}
