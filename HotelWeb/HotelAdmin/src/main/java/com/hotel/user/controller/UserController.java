package com.hotel.user.controller;

import com.hotel.common.entity.User;
import com.hotel.user.exception.HotelNotFoundException;
import com.hotel.user.model.dto.reponse.AdminInfo;
import com.hotel.user.model.dto.reponse.HotelResponse;
import com.hotel.user.model.dto.request.command.UpdateAdminCommand;
import com.hotel.user.service.HotelService;
import com.hotel.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserService userService;
    //api tìm kiếm hotel theo keyword and userId( nếu keyword là null thì trả về tất cả hotel ứng với userId đó)
    @GetMapping("/{email}/hotels")
    public ResponseEntity<?> getHotel(@PathVariable("email") String email,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                      @RequestParam(value = "keyword", required = false) String keyword) {
        User user = userService.getUser(email);
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<HotelResponse> hotels = hotelService.getAllHotels_v2(user.getId(), keyword, pageable);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getProfile(@PathVariable("email") String email) {
        User admin = userService.getUser(email);
        return ResponseEntity.ok(AdminInfo.getAdminInfo(admin));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateAdminCommand command) {
        // Gọi service để thực hiện cập nhật
        try {
            userService.updateUser(command);
            return ResponseEntity.ok("Admin updated successfully");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


}
