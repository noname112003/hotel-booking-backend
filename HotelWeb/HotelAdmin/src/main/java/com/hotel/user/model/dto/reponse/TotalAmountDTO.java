package com.hotel.user.model.dto.reponse;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
public class TotalAmountDTO {
    private BigDecimal totalAmount;
}
