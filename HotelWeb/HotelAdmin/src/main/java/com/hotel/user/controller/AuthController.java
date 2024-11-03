package com.hotel.user.controller;

import com.hotel.common.entity.User;
import com.hotel.user.model.dto.reponse.ResponseObject;
import com.hotel.user.model.dto.reponse.UserResponse;
import com.hotel.user.model.dto.request.UserRequest;

import com.hotel.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody @Valid UserRequest userRequest , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(ObjectError :: getDefaultMessage ).collect(Collectors.joining("\n")));
        }
        UserResponse userResponse = userService.login(userRequest);
        System.out.println(userResponse);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(userResponse)
                .build());
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(ObjectError:: getDefaultMessage ).collect(Collectors.joining("\n")));
        }
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }
}
