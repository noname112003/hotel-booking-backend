package com.hotel.common.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(length = 2000)
    private String description;

    private String checkin;

    private String checkout;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Tên cột trong bảng Hotel
    private User user;

    // Liên kết với danh sách Room
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    // Liên kết với danh sách Image
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image_hotel> images;

    private Boolean parking;
    private Boolean keep_luggage;
    private Boolean free_wifi;
    private Boolean laundry_service;
    private Boolean room_service;


}
