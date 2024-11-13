package com.hotel.customer.controller;

import com.hotel.common.entity.Customer;
import com.hotel.customer.model.dto.request.command.UpdateCustomerCommand;
import com.hotel.customer.model.dto.response.CustomerInfo;
import com.hotel.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{email}")
    public ResponseEntity<CustomerInfo> findById(@PathVariable String email) {
        return  ResponseEntity.ok(customerService.getCustomerInfo(email));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid UpdateCustomerCommand command, BindingResult bindingResult) {
        // Kiểm tra lỗi validation
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
            return ResponseEntity.badRequest().body(errors);
        }

        // Gọi service để thực hiện cập nhật
        try {
            customerService.updateCustomer(command);
            return ResponseEntity.ok("Customer updated successfully");
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


}
