package com.data.ptit_ks23b_185_nguyentheminh_01.service.busRoute;

import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.BusRoute;
import com.data.ptit_ks23b_185_nguyentheminh_01.repository.BusRepository;
import com.data.ptit_ks23b_185_nguyentheminh_01.repository.BusRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusRouteServiceImp implements BusRouteService{

    private final BusRouteRepository busRouteRepository;
    private final BusRepository busRepository;

    @Override
    public BusRoute findBusRouteById(int id) {
        return busRouteRepository.findById(id).orElse(null);
    }

    @Override
    public BusRoute saveBusRoute(BusRoute busRoute) {
        if(busRoute == null) return null;

        // Validate bus tồn tại
        if(busRoute.getBus() == null || busRoute.getBus().getId() == null ||
                busRepository.findById(busRoute.getBus().getId()).isEmpty()){
            throw new IllegalArgumentException("Bus không hợp lệ");
        }

        return busRouteRepository.save(busRoute);
    }

    @Override
    public BusRoute updateBusRoute(int id, BusRoute busRoute) {
        BusRoute existing = findBusRouteById(id);
        if(existing == null) return null;

        if(busRoute.getBus() == null || busRoute.getBus().getId() == null ||
                busRepository.findById(busRoute.getBus().getId()).isEmpty()){
            throw new IllegalArgumentException("Bus không hợp lệ");
        }

        existing.setStartPoint(busRoute.getStartPoint());
        existing.setEndPoint(busRoute.getEndPoint());
        existing.setTripInformation(busRoute.getTripInformation());
        existing.setDriverName(busRoute.getDriverName());
        existing.setBus(busRoute.getBus());
        existing.setStatus(busRoute.isStatus());

        return busRouteRepository.save(existing);
    }

    @Override
    public boolean deleteBusRoute(int id) {
        BusRoute busRoute = findBusRouteById(id);
        if(busRoute == null) return false;

        busRoute.setStatus(false);
        busRouteRepository.save(busRoute);
        return true;
    }

    @Override
    public List<BusRoute> findBusRouteByBusId(int busId) {
        return busRouteRepository.findBusRouteByBusId(busId);
    }

    @Override
    public List<BusRoute> findAllBusRoutes() {
        List<BusRoute> all = busRouteRepository.findAll();

        return all.stream()
                .sorted(Comparator.comparing(BusRoute::getStartPoint))
                .collect(Collectors.toList());
    }

    @Override
    public List<BusRoute> searchRoutes(String startPoint, String endPoint) {
        return busRouteRepository.searchRoutes(
                (startPoint == null || startPoint.trim().isEmpty()) ? null : startPoint.trim(),
                (endPoint == null || endPoint.trim().isEmpty()) ? null : endPoint.trim()
        );
    }
}

