package com.hotel.user.model.dto.request.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class UpdateAdminCommand {
    private String name;

    @NotBlank
    private String email;

    private String phoneNumber;

    private String identification;
}
