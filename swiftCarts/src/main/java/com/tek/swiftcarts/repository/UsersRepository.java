package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users,String> {

    Optional<Users> findByKeyCloakId(String keyCloakId);
}
