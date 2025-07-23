package com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {
    private Integer id;
    @NotBlank(message = "Tên xe không được để trống")
    private String name;
    @NotBlank(message = "Biển số xe không được để trống")
    private String registrationNumber;
    @Positive(message = "Tổng số ghế phải lớn hơn 0")
    private int totalSeats;
    private MultipartFile imageBus;
    private boolean status = true;
}
