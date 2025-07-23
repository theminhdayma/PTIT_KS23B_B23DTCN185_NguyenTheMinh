package com.data.ptit_ks23b_185_nguyentheminh_01.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bus_route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_route_id")
    private Integer id;
    @Column(name = "start_point", columnDefinition = "varchar(255)", nullable = false)
    private String startPoint;
    @Column(name = "end_point", columnDefinition = "varchar(255)", nullable = false)
    private String endPoint;
    @Column(name = "trip_information", columnDefinition = "varchar(255)", nullable = false)
    private String tripInformation;
    @Column(name = "driver_name", columnDefinition = "varchar(70)", nullable = false)
    private String driverName;
    @Column(name = "status", columnDefinition = "BIT DEFAULT 1")
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "bus_id", nullable = false)
    private Bus bus;
}


