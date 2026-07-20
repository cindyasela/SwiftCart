package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.DeliveryType;
import lombok.experimental.Delegate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType,String> {


}
