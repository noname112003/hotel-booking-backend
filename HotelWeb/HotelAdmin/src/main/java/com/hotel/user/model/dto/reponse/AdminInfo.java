package com.hotel.user.model.dto.reponse;

import com.hotel.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminInfo {
    private String name ;
    private String email;
    private String phoneNumber;
    private String identification;

    public static AdminInfo getAdminInfo(@NotNull User user) {
        return new AdminInfo(user.getName(), user.getEmail(), user.getPhoneNumber(), "");
    }
}
