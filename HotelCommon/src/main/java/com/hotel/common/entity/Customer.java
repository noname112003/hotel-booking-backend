package com.hotel.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name cannot be empty \n ")
    private String name;
    @NotEmpty(message = "Email cannot be empty \n")
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phoneNumber ;
    @Column(unique = true)
    private String identification;
}
