package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {

    Optional<Staff> findByKeyCloakId(String keyCloakId);
}
