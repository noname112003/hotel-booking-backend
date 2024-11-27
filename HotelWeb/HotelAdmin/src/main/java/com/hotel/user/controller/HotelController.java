package com.hotel.user.controller;


import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.User;
import com.hotel.user.exception.HotelNotFoundException;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.reponse.RoomResponse;
import com.hotel.user.model.dto.request.HotelRequest;
import com.hotel.user.service.HotelService;
import com.hotel.user.service.RoomService;
import com.hotel.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid HotelRequest hotelRequest, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(ObjectError:: getDefaultMessage ).collect(Collectors.joining("\n")));
        }
        return new ResponseEntity<>(hotelService.registerHotel(hotelRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotel(@PathVariable Long id) {
        try {
            HotelResponse hotelResponse = hotelService.getHotel(id);
            return ResponseEntity.ok(hotelResponse);
        } catch (HotelNotFoundException ex) {
            // Trả về mã lỗi 404 khi không tìm thấy khách sạn
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            // Xử lý lỗi không mong đợi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    @GetMapping("/findall/{email}")
    public ResponseEntity<?> getHotelsByID(@PathVariable String email) {
        User user = userService.getUser(email);
        try {
            List<HotelResponse> hotelResponses = hotelService.getAllHotels(user.getId());
            return ResponseEntity.ok(hotelResponses);
        } catch (Exception ex) {
            // Xử lý lỗi không mong đợi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }


    @PutMapping("/update/{hotelId}")
    public ResponseEntity<HotelResponse> updateHotel(
            @PathVariable Long hotelId,
            @RequestBody @Valid HotelRequest hotelRequest) {

        HotelResponse updatedHotel = hotelService.updateHotel(hotelId, hotelRequest);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<?> getRoomsByHotelId(@PathVariable Long hotelId) {

        List<RoomResponse> rooms = roomService.getRoomsByHotelId(hotelId);
        return ResponseEntity.ok(rooms);

    }

    @DeleteMapping("/{hotelId}/rooms/delete/{roomId}")
    public ResponseEntity<?> deleteRoomFromHotel(@PathVariable("hotelId") Long hotelId, @PathVariable("roomId") Long roomId) {
        hotelService.deleteRoomFromHotel(hotelId, roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Trả về 204 nếu xóa thành công
    }



}
