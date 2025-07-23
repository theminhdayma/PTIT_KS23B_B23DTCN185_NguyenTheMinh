package com.data.ptit_ks23b_185_nguyentheminh_01.service.bus;

import com.data.ptit_ks23b_185_nguyentheminh_01.model.dto.request.BusDto;
import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.Bus;

import java.util.List;

public interface BusService {
    List<Bus> findAllBuses();
    Bus findBusById(Integer id);
    Bus saveBus(BusDto busDto);
    Bus updateBus(Integer id, BusDto busDto);
    void deleteBus(Integer id);
}
