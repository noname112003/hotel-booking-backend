package com.hotel.user.controller;

import com.hotel.user.exception.HotelNotFoundException;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private HotelService hotelService;
    //api tìm kiếm hotel theo keyword and userId( nếu keyword là null thì trả về tất cả hotel ứng với userId đó)
    @GetMapping("/{id}/hotels")
    public ResponseEntity<?> getHotel(@PathVariable("id") Long userId,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                      @RequestParam(value = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<HotelResponse> hotels = hotelService.getAllHotels_v2(userId, keyword, pageable);
        return ResponseEntity.ok(hotels);
    }

}
