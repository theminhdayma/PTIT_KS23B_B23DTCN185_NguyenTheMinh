package com.data.ptit_ks23b_185_nguyentheminh_01.controller;

import com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.request.BusRouteDto;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.response.APIResponse;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.Bus;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.BusRoute;
import com.data.ptit_ks23b_185_nguyentheminh_01.service.bus.BusService;
import com.data.ptit_ks23b_185_nguyentheminh_01.service.busRoute.BusRouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bus-routes")
@RequiredArgsConstructor
public class BusRouteController {

    private final BusRouteService busRouteService;
    private final BusService busService;

    private BusRouteDto convertEntityToDto(BusRoute entity){
        BusRouteDto dto = new BusRouteDto();
        dto.setId(entity.getId());
        dto.setBusId(entity.getBus().getId());
        dto.setStartPoint(entity.getStartPoint());
        dto.setEndPoint(entity.getEndPoint());
        dto.setTripInformation(entity.getTripInformation());
        dto.setDriverName(entity.getDriverName());
        dto.setStatus(entity.isStatus());
        return dto;
    }

    private BusRoute convertDtoToEntity(BusRouteDto dto) {
        Bus bus = busService.findBusById(dto.getBusId());
        if(bus == null){
            return null;
        }
        return BusRoute.builder()
                .id(dto.getId())
                .bus(bus)
                .startPoint(dto.getStartPoint())
                .endPoint(dto.getEndPoint())
                .tripInformation(dto.getTripInformation())
                .driverName(dto.getDriverName())
                .status(dto.isStatus())
                .build();
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<BusRouteDto>>> getAllBusRoutes() {
        List<BusRoute> busRoutes = busRouteService.findAllBusRoutes();

        List<BusRouteDto> dtos = busRoutes.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new APIResponse<>("success", dtos, null, 200));
    }

    @PostMapping
    public ResponseEntity<?> createBusRoute(@Valid @RequestBody BusRouteDto busRouteDto,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, bindingResult.getFieldError().getDefaultMessage(), 400));
        }

        BusRoute busRoute = convertDtoToEntity(busRouteDto);
        if(busRoute == null){
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, "Xe buýt không hợp lệ", 400));
        }

        try {
            BusRoute saved = busRouteService.saveBusRoute(busRoute);
            return ResponseEntity.status(201)
                    .body(new APIResponse<>("success", convertEntityToDto(saved), "Thêm chuyến đi thành công", 201));
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, e.getMessage(), 400));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBusRoute(@PathVariable int id, @Valid @RequestBody BusRouteDto busRouteDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, bindingResult.getFieldError().getDefaultMessage(), 400));
        }

        BusRoute busRoute = convertDtoToEntity(busRouteDto);
        if(busRoute == null){
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, "Xe buýt không hợp lệ", 400));
        }

        BusRoute updated = busRouteService.updateBusRoute(id, busRoute);
        if(updated == null){
            return ResponseEntity.badRequest()
                    .body(new APIResponse<>("error", null, "Không tìm thấy chuyến đi để cập nhật", 400));
        }

        return ResponseEntity.ok(new APIResponse<>("success", convertEntityToDto(updated), "Cập nhật chuyến đi thành công", 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBusRoute(@PathVariable int id){
        boolean success = busRouteService.deleteBusRoute(id);
        if(!success){
            return ResponseEntity.badRequest().body(new APIResponse<>("error", null, "Không tìm thấy chuyến đi để xóa", 400));
        }
        return ResponseEntity.ok(new APIResponse<>("success", null, "Xóa chuyến đi thành công", 200));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBusRoutes(
            @RequestParam(required = false) String startPoint,
            @RequestParam(required = false) String endPoint,
            @RequestParam(required = false) Integer id) {

        if(id != null){
            BusRoute busRoute = busRouteService.findBusRouteById(id);
            if(busRoute == null){
                return ResponseEntity.badRequest().body(new APIResponse<>("error", null, "Không tìm thấy chuyến đi với mã: "+id, 400));
            }
            return ResponseEntity.ok(new APIResponse<>("success", List.of(convertEntityToDto(busRoute)), null, 200));
        }

        List<BusRoute> busRoutes = busRouteService.searchRoutes(startPoint, endPoint);
        List<BusRouteDto> dtos = busRoutes.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new APIResponse<>("success", dtos, null, 200));
    }

}

