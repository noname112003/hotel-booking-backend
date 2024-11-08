package com.hotel.customer.controller;

import com.hotel.common.entity.Customer;
import com.hotel.common.entity.User;
import com.hotel.customer.model.dto.request.CustomerRequest;
import com.hotel.customer.model.dto.response.CustomerResponse;
import com.hotel.customer.model.dto.response.ResponseObject;
import com.hotel.customer.service.CustomerService;
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
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid CustomerRequest customerRequest , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(ObjectError:: getDefaultMessage ).collect(Collectors.joining("\n")));
        }
        CustomerResponse customerResponse = customerService.login(customerRequest);
        System.out.println(customerResponse);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Successfully")
                .status(HttpStatus.OK)
                .data(customerResponse)
                .build());
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer , BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().stream().map(ObjectError:: getDefaultMessage ).collect(Collectors.joining("\n")));
        }
        return new ResponseEntity<>(customerService.registerCustomer(customer), HttpStatus.CREATED);
    }
}
