package com.data.ptit_ks23b_185_nguyentheminh_01.service.bus;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.request.BusDto;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.Bus;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.BusRoute;
import com.data.ptit_ks23b_185_nguyentheminh_01.repository.BusRepository;
import com.data.ptit_ks23b_185_nguyentheminh_01.repository.BusRouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BusServiceImp implements BusService {

    private final BusRepository busRepository;
    private final BusRouteRepository busRouteRepository;
    private final Cloudinary cloudinary;

    @Override
    public List<Bus> findAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Bus findBusById(Integer id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe buýt với id: " + id));
    }

    private String uploadToCloudinary(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "bus_images"));
            return (String) result.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Lỗi upload ảnh lên Cloudinary: " + e.getMessage(), e);
        }
    }

    @Override
    public Bus saveBus(BusDto busDto){
        if(busDto == null) {
            throw new IllegalArgumentException("BusDto không được null");
        }

        Optional<Bus> existingBus = busRepository.findByName(busDto.getName());
        if(existingBus.isPresent()){
            throw new IllegalArgumentException("Tên xe buýt đã tồn tại");
        }

        Optional<Bus> existingRegistrationNumber = busRepository.findByRegistrationNumber(busDto.getRegistrationNumber());
        if(existingRegistrationNumber.isPresent()){
            throw new IllegalArgumentException("Biển số xe đã tồn tại");
        }

        String imageUrl = uploadToCloudinary(busDto.getImageBus());

        Bus bus = Bus.builder()
                .name(busDto.getName())
                .registrationNumber(busDto.getRegistrationNumber())
                .totalSeats(busDto.getTotalSeats())
                .status(busDto.isStatus())
                .imageBus(imageUrl)
                .build();

        return busRepository.save(bus);
    }

    @Override
    public Bus updateBus(Integer id, BusDto busDto) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe buýt với id: " + id));

        Optional<Bus> existingBus = busRepository.findByNameAndIdNot(busDto.getName(), id);
        if(existingBus.isPresent()){
            throw new IllegalArgumentException("Tên xe buýt đã tồn tại");
        }

        Optional<Bus> existingRegistrationNumber = busRepository.findByRegistrationNumberAndIdNot(busDto.getRegistrationNumber(), id);
        if(existingRegistrationNumber.isPresent()){
            throw new IllegalArgumentException("Biển số xe đã tồn tại");
        }

        bus.setName(busDto.getName());
        bus.setRegistrationNumber(busDto.getRegistrationNumber());
        bus.setTotalSeats(busDto.getTotalSeats());
        bus.setStatus(busDto.isStatus());

        MultipartFile newImage = busDto.getImageBus();
        if(newImage != null && !newImage.isEmpty()){
            String newImageUrl = uploadToCloudinary(newImage);
            bus.setImageBus(newImageUrl);
        }

        return busRepository.save(bus);
    }

    @Override
    public void deleteBus(Integer id){
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe buýt với id: " + id));

        List<BusRoute> busRoutes = busRouteRepository.findBusRouteByBusId(bus.getId());

        if(busRoutes != null && !busRoutes.isEmpty()){
            throw new RuntimeException("Không thể xóa xe buýt này vì nó đang được sử dụng trong các tuyến xe buýt");
        }

        if(bus.isStatus()){
            bus.setStatus(false);
            busRepository.save(bus);
        }
    }
}
