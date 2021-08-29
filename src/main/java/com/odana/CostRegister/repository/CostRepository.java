package com.odana.CostRegister.repository;

import com.odana.CostRegister.model.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CostRepository extends JpaRepository<Cost, Long> {
    @Query("SELECT c FROM Cost c WHERE c.user=?3 AND c.timestamp BETWEEN ?1 AND ?2")
    public List<Cost> findByUserAndInterval(LocalDateTime start, LocalDateTime end, String user);
}
