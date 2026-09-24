package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,String> {

    boolean existsByShiftAssignment_IdAndClockOutIsNull(String shiftAssignmentId);

    List<Attendance> findByStaff_IdOrderByClockInDesc(String staffId);
}
