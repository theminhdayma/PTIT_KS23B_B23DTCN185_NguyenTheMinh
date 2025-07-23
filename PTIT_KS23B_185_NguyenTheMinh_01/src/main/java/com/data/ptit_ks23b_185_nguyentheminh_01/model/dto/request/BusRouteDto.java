package com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusRouteDto {
    private Integer id;
    @NotNull(message = "ID xe buýt không được để trống")
    @Positive(message = "ID xe buýt phải là số dương")
    private Integer busId;
    @NotBlank(message = "Điểm xuất phát không được để trống")
    private String startPoint;
    @NotBlank(message = "Điểm kết thúc không được để trống")
    private String endPoint;
    @NotBlank(message = "Thông tin chuyến đi không được để trống")
    private String tripInformation;
    @NotBlank(message = "Tên tài xế không được để trống")
    private String driverName;
    private boolean status = true;
}
