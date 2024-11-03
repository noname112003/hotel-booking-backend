package com.hotel.customer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerResponse {
        private Long id ;
        private String token ;
        private String name ;
        private String email;
}
