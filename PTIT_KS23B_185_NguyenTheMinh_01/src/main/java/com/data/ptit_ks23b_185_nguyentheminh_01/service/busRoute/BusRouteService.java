package com.data.ptit_ks23b_185_nguyentheminh_01.service.busRoute;


import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.BusRoute;

import java.util.List;

public interface BusRouteService {
    BusRoute findBusRouteById(int id);
    BusRoute saveBusRoute(BusRoute busRoute);
    BusRoute updateBusRoute(int id, BusRoute busRoute);
    boolean deleteBusRoute(int id);
    List<BusRoute> findBusRouteByBusId(int busId);
    List<BusRoute> findAllBusRoutes();
    List<BusRoute> searchRoutes(String startPoint, String endPoint);
}

