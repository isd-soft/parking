package com.isd.parking.repository;

import com.isd.parking.model.StatsRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StatsRepo extends JpaRepository<StatsRow, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM stats s WHERE s.updatedAt < :date")
    int removeOlderThan(@Param("date") java.sql.Date date);

}
