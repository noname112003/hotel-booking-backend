package com.hotel.user.controller;

import com.hotel.user.exception.HotelNotFoundException;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private HotelService hotelService;
    @GetMapping("/{id}/hotels")
    public ResponseEntity<?> getHotel(@PathVariable("id") Long userId) {
        List<HotelResponse> hotels = hotelService.getAllHotels(userId);
        return ResponseEntity.ok(hotels);
    }
}
