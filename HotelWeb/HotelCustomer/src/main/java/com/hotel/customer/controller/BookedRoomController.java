package com.hotel.customer.controller;


import com.hotel.common.entity.Booked_room;
import com.hotel.common.entity.Customer;
import com.hotel.customer.model.dto.request.BookRoomRequest;
import com.hotel.customer.model.dto.response.HistoryBooking;
import com.hotel.customer.service.BookedRoomService;
import com.hotel.customer.service.CustomerService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/booked-rooms")
@RequiredArgsConstructor
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/book")
    public ResponseEntity<?> bookRoom(@RequestBody BookRoomRequest request) {
        Customer customer = customerService.getCustomer(request.getEmail());
        try {
            Booked_room bookedRoom = bookedRoomService.bookRoom(
                    request.getRoomId(),
                    customer.getId(),
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
    @GetMapping("/history/{email}")
    public ResponseEntity<List<HistoryBooking>> getBookingHistory(@PathVariable String email) {
        Customer customer = customerService.getCustomer(email);
        List<HistoryBooking> history = bookedRoomService.getHistoryByUserId(customer.getId());
        history.sort(Comparator.comparing(HistoryBooking::getBookingDate));
        return ResponseEntity.ok(history);
    }
}
