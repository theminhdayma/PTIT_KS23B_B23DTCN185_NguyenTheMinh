package com.data.ptit_ks23b_185_nguyentheminh_01.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "bus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer id;
    @Column(name = "bus_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String name;
    @Column(name = "registration_number", columnDefinition = "varchar(30)", nullable = false, unique = true)
    private String registrationNumber;
    @Column(name = "total_seats", nullable = false)
    private int totalSeats;
    @Column(name = "image_bus", columnDefinition = "varchar(255)")
    private String imageBus;
    @Column(name = "status", columnDefinition = "BIT DEFAULT 1")
    private boolean status = true;

    @OneToMany(mappedBy = "bus")
    @JsonIgnore
    private List<BusRoute> busRoutes;
}

