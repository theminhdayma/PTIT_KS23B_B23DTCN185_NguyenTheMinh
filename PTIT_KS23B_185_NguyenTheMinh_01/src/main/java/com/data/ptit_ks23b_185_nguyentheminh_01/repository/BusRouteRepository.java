package com.data.ptit_ks23b_185_nguyentheminh_01.repository;

import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute,Integer> {
    @Query("SELECT br FROM BusRoute br WHERE br.bus.id = :busId")
    List<BusRoute> findBusRouteByBusId(int busId);

    @Query("SELECT br FROM BusRoute br " +
            "WHERE (:startPoint IS NULL OR br.startPoint LIKE %:startPoint%) " +
            "AND (:endPoint IS NULL OR br.endPoint LIKE %:endPoint%)")
    List<BusRoute> searchRoutes(String startPoint, String endPoint);

}

