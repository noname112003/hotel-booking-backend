package com.hotel.user.service;

import com.hotel.user.model.dto.reponse.BookedRoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookedRoomService {
    Page<BookedRoomDTO> getBookedRoomsForAdmin(String adminEmail, Pageable pageable);
}
