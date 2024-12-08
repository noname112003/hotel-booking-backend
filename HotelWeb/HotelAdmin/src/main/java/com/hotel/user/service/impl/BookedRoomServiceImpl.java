package com.hotel.user.service.impl;

import com.hotel.common.entity.Booked_room;
import com.hotel.user.model.dto.reponse.BookedRoomDTO;
import com.hotel.user.repository.BookedRoomRepository;
import com.hotel.user.service.BookedRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class BookedRoomServiceImpl implements BookedRoomService {

    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Override
    public Page<BookedRoomDTO> getBookedRoomsForAdmin(String adminEmail, Pageable pageable) {
        // Lấy ngày hiện tại
        Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Lấy dữ liệu Booked_room theo email admin và ngày hiện tại
        Page<Booked_room> bookedRoomsPage = bookedRoomRepository.findByAdminEmailAndBookingDate(adminEmail, today, pageable);

        // Chuyển đổi từng phần tử từ Booked_room sang BookedRoomDTO
        return bookedRoomsPage.map(bookedRoom -> {
            // Tính số ngày đã ở
            long daysStayed = ChronoUnit.DAYS.between(
                    bookedRoom.getCheckinDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    bookedRoom.getCheckoutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );

            // Tính tổng chi phí
            BigDecimal roomPrice = BigDecimal.valueOf(bookedRoom.getRoom().getPrice());
            BigDecimal totalCost = roomPrice.multiply(BigDecimal.valueOf(daysStayed));

            return BookedRoomDTO.builder()
                    .id(bookedRoom.getId())
                    .hotelName(bookedRoom.getRoom().getHotel().getName()) // Lấy tên khách sạn
                    .roomName(bookedRoom.getRoom().getRoomType()) // Lấy loại phòng
                    .customerName(bookedRoom.getCustomer().getName()) // Lấy tên khách hàng
                    .checkinDate(bookedRoom.getCheckinDate()) // Ngày check-in
                    .checkoutDate(bookedRoom.getCheckoutDate()) // Ngày check-out
                    .bookingDate(bookedRoom.getBookingDate()) // Ngày đặt phòng
                    .totalCost(totalCost) // Tổng chi phí
                    .build();
        });
    }


}
