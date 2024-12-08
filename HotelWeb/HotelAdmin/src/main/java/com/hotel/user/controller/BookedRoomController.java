package com.hotel.user.controller;

import com.hotel.user.model.dto.reponse.BookedRoomDTO;
import com.hotel.user.service.BookedRoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookedRoomController {
    private BookedRoomService bookedRoomService;

    @GetMapping("/api/admin/booked-rooms")
    public Page<BookedRoomDTO> getBookedRoomsForAdmin(@RequestParam("email") String adminEmail,
                                                      @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "bookingDate"));
        return bookedRoomService.getBookedRoomsForAdmin(adminEmail, pageable);
    }
}
