package com.hotel.customer.model.dto.response;

import com.hotel.common.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerInfo {

    private String name ;
    private String email;
    private String phoneNumber;
    private String identification;

    @NonNull
    public static CustomerInfo convertDTO(@NotNull Customer customer) {
        return new CustomerInfo(
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getIdentification());
    }
}
