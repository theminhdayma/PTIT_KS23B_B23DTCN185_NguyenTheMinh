package com.data.ptit_ks23b_185_nguyentheminh_01.repository;

import com.data.ptit_ks23b_185_nguyentheminh_01.model.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus,Integer> {
    Optional<Bus> findByName(String name);
    Optional<Bus> findByNameAndIdNot(String name, Integer id);
    Optional<Bus> findByRegistrationNumber(String registrationNumber);
    Optional<Bus> findByRegistrationNumberAndIdNot(String registrationNumber, Integer id);
}
