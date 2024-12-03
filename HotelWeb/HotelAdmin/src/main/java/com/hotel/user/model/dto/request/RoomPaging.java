package com.hotel.user.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoomPaging {

    private List<Long> hotelIds;

    private int page;

    private int size;

    private String keyword;
}
