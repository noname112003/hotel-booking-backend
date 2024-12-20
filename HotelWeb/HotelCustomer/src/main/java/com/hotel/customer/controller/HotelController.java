package com.hotel.customer.controller;

import com.hotel.common.entity.Room;
import com.hotel.customer.model.dto.response.HotelResponse;
import com.hotel.customer.model.dto.response.RoomResponse;
import com.hotel.customer.service.HotelService;
import com.hotel.customer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    // tìm kiếm hotel dựa vào keyword và còn phòng trống vào khoảng thời gian checkin checkout
    @GetMapping("/findall")
    public ResponseEntity<Page<HotelResponse>> searchHotels(
            @RequestParam(value = "checkin", required = false) Date checkin,
            @RequestParam(value = "checkout", required = false) Date checkout,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size
            ) {
        // Gọi service để tìm kiếm khách sạn theo điều kiện checkin, checkout và từ khóa tìm kiếm
        Pageable pageable = PageRequest.of(page, size);
        Page<HotelResponse> hotels = hotelService.searchHotelsWithAvailableRooms(checkin, checkout, keyword, pageable);
        return ResponseEntity.ok(hotels);
    }
    // api lây ra room trong hotel thoả mãn điều kiện checkin checkout
    @GetMapping("/hotels/{hotelId}/rooms")
    public ResponseEntity<Page<RoomResponse>> getAvailableRooms(@PathVariable Long hotelId,
                                                                @RequestParam Date checkin,
                                                                @RequestParam Date checkout,
                                                                @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<RoomResponse> rooms = roomService.getAvailableRooms(hotelId, checkin, checkout, pageable);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/destination")
    public ResponseEntity<Page<HotelResponse>> getDestinations(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HotelResponse> destinations = hotelService.searchDestinations(keyword, pageable);
        return ResponseEntity.ok(destinations);
    }


}
