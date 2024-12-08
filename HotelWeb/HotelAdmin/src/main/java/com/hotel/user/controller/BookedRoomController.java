package com.hotel.user.controller;

import com.hotel.user.model.dto.reponse.BookedRoomDTO;
import com.hotel.user.model.dto.reponse.TotalAmountDTO;
import com.hotel.user.service.BookedRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin/booking")
public class BookedRoomController {
    @Autowired
    private BookedRoomService bookedRoomService;

    @GetMapping("/booked-rooms")
    public Page<BookedRoomDTO> getBookedRoomsForAdmin(@RequestParam("email") String adminEmail,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "bookingDate"));
        return bookedRoomService.getBookedRoomsForAdmin(adminEmail, pageable);
    }

    @GetMapping("/order/findall")
    public Page<BookedRoomDTO> getAllOrder(@RequestParam("email") String adminEmail,
                                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "bookingDate"));
        return bookedRoomService.getAllBookedRoom(adminEmail, pageable);
    }

    @GetMapping("/total")
    public TotalAmountDTO getTotalAmount(@RequestParam("email") String adminEmail) {
        return bookedRoomService.caculateTotalAmount(adminEmail);
    }

}
