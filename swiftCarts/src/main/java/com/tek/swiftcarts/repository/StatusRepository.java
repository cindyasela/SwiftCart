package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository <Status,String> {

    Optional<Status> findByName(@Param("name") String name);

}
