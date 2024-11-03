package com.hotel.customer.security.user_principle;


import com.hotel.common.entity.Customer;
import com.hotel.common.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Builder
public class HotelCustomerDetails implements UserDetails {
    private Long id;
    private String name;
    private  String email;
    private String password;
    private Customer customer ;

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public static HotelCustomerDetails buildUserDetails(Customer customer){

        return HotelCustomerDetails.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .password(customer.getPassword()).build();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
