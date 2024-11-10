package com.hotel.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Booked_room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với Room
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Liên kết với Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "checkin_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkinDate;

    @Column(name = "checkout_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;

    @Column(name = "booking_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @Column(name = "code", nullable = false, length = 50)
    private String code;
}
