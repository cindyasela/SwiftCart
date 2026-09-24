package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment,String> {

    List<ShiftAssignment> findStaffByShiftId(String shiftId);

    List<ShiftAssignment> findByStaff_Id(String staffId);
}
