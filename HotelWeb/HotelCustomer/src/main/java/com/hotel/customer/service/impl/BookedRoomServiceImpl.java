package com.hotel.customer.service.impl;

import com.hotel.common.entity.Booked_room;
import com.hotel.common.entity.Customer;
import com.hotel.common.entity.Hotel;
import com.hotel.common.entity.Room;
import com.hotel.customer.exception.HotelNotFoundException;
import com.hotel.customer.exception.RoomNotFoundException;
import com.hotel.customer.model.dto.response.HistoryBooking;
import com.hotel.customer.repository.BookedRoomRepository;
import com.hotel.customer.repository.CustomerRepository;
import com.hotel.customer.repository.RoomRepository;
import com.hotel.customer.service.BookedRoomService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BookedRoomServiceImpl implements BookedRoomService {
    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JavaMailSender mailSender;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateBookingCode() {
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }

    public void sendHtmlEmail(String subject, String name, String phone, String message, String emailCustomer, String nameHotel) throws MessagingException, UnsupportedEncodingException {
        // Tạo nội dung HTML
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email Template</title>
                </head>
                <body>
                    <h2>Xin chào, %s!</h2>
                    <p>Đây là thông tin mà bạn đã gửi:</p>
                    <ul>
                        <li>Email: %s</li>
                        <li>Số điện thoại: %s</li>
                        <li>Nội dung: %s</li>
                    </ul>
                    <p>Cảm ơn bạn đã liên hệ với chúng tôi.</p>
                </body>
                </html>
                """.formatted(name, emailCustomer, phone, message);

        // Tạo email
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(emailCustomer);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true để gửi email HTML
        helper.setFrom("quangteo7112003@gmail.com", nameHotel);

        // Gửi email
        mailSender.send(mimeMessage);
    }

    public Booked_room bookRoom(Long roomId, Long customerId, Date checkinDate, Date checkoutDate) throws MessagingException, UnsupportedEncodingException {
        // Kiểm tra phòng có tồn tại hay không
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

        Hotel hotel = room.getHotel();
        if (hotel == null) {
            throw new IllegalStateException("Hotel not found for room with id: " + roomId);
        }

        // Kiểm tra khách hàng có tồn tại hay không
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new HotelNotFoundException("Customer not found with id: " + customerId));

        // Kiểm tra phòng có khả dụng trong khoảng thời gian yêu cầu hay không
        boolean isAvailable = isRoomAvailable(roomId, checkinDate, checkoutDate);
        if (!isAvailable) {
            throw new IllegalStateException("Room is not available for the selected dates.");
        }

        // Tạo mã đặt phòng duy nhất
        String bookingCode = generateBookingCode();

        // Tạo đối tượng Booked_room
        Booked_room bookedRoom = Booked_room.builder()
                .room(room)
                .customer(customer)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .bookingDate(new Date()) // Ngày đặt phòng là ngày hiện tại
                .code(bookingCode)
                .build();
        // Gửi email thông báo
        String subject = "Xác nhận đặt phòng tại " + hotel.getName();
        String message = """
            Bạn đã đặt phòng thành công tại khách sạn %s. 
            Mã đặt phòng của bạn là: %s.
            Thời gian check-in: %s.
            Thời gian check-out: %s.
            Phòng: %s.
            """.formatted(hotel.getName(), bookingCode, checkinDate, checkoutDate, room.getRoomType());

        sendHtmlEmail(subject, customer.getName(), customer.getPhoneNumber(), message, customer.getEmail(), hotel.getName());

        // Lưu đối tượng vào cơ sở dữ liệu
        return bookedRoomRepository.save(bookedRoom);
    }

    private boolean isRoomAvailable(Long roomId, Date checkinDate, Date checkoutDate) {
        return bookedRoomRepository.findConflictingBookings(roomId, checkinDate, checkoutDate).isEmpty();
    }

    @Override
    public List<HistoryBooking> getHistoryByUserId(Long userId) {
        List<Booked_room> bookedRooms = bookedRoomRepository.findByCustomerId(userId);

        return bookedRooms.stream().map(bookedRoom -> {
            // Tính số ngày đã ở
            long daysStayed = ChronoUnit.DAYS.between(
                    bookedRoom.getCheckinDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    bookedRoom.getCheckoutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            );

            // Tính tổng chi phí
            BigDecimal totalCost = bookedRoom.getRoom().getPrice()
                    .multiply(BigDecimal.valueOf(daysStayed));

            return HistoryBooking.builder()
                    .id(bookedRoom.getId())
                    .nameHotel(bookedRoom.getRoom().getHotel().getName())
                    .roomType(bookedRoom.getRoom().getRoomType())
                    .checkinDate(bookedRoom.getCheckinDate())
                    .checkoutDate(bookedRoom.getCheckoutDate())
                    .bookingDate(bookedRoom.getBookingDate())
                    .totalCost(totalCost)
                    .build();
        }).collect(Collectors.toList());
    }


}
