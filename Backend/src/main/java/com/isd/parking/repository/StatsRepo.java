package com.isd.parking.repository;

import com.isd.parking.model.StatsRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepo extends JpaRepository<StatsRow, Long> {
}
