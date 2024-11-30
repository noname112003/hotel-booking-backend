package com.hotel.customer.model.dto.request.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class UpdateCustomerCommand {

    private String name;

    @NotBlank
    private String email;

    private String phoneNumber;

    private String identification;
}
