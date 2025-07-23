package com.data.ptit_ks23b_185_nguyentheminh_01.controller;

import com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.request.BusDto;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.response.APIResponse;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.Bus;
import com.data.ptit_ks23b_185_nguyentheminh_01.service.bus.BusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bus")
public class BusController {

    private final BusService busService;

    private BusDto convertEntityToDto(Bus bus){
        BusDto dto = new BusDto();
        dto.setId(bus.getId());
        dto.setName(bus.getName());
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setStatus(bus.isStatus());
        return dto;
    }

    private Bus convertDtoToEntity(BusDto dto){
        Bus bus = new Bus();
        bus.setId(dto.getId());
        bus.setName(dto.getName());
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setTotalSeats(dto.getTotalSeats());
        bus.setStatus(dto.isStatus());
        return bus;
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<BusDto>>> getAllBuses() {
        List<Bus> buses = busService.findAllBuses();
        List<BusDto> dtos = buses.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new APIResponse<>("success", dtos, null, 200));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBus(@Valid @ModelAttribute BusDto busDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null,
                            bindingResult.getFieldError().getDefaultMessage(), 400));
        }
        try {
            Bus bus = busService.saveBus(busDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse<>("success", convertEntityToDto(bus), "Thêm mới xe buýt thành công", 201));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, e.getMessage(), 400));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBus(@PathVariable Integer id,
                                       @Valid @ModelAttribute BusDto busDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null,
                            bindingResult.getFieldError().getDefaultMessage(), 400));
        }
        try {
            Bus updatedBus = busService.updateBus(id, busDto);
            return ResponseEntity.ok(new APIResponse<>("success", convertEntityToDto(updatedBus), "Cập nhật xe buýt thành công", 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, e.getMessage(), 400));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable Integer id) {
        try {
            busService.deleteBus(id);
            return ResponseEntity.ok(new APIResponse<>("success", null, "Xóa (thay đổi trạng thái) xe buýt thành công", 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, e.getMessage(), 400));
        }
    }
}
