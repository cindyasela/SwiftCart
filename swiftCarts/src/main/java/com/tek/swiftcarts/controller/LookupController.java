package com.tek.swiftcarts.controller;

import com.tek.swiftcarts.config.DataSeeder;
import com.tek.swiftcarts.dto.*;
import com.tek.swiftcarts.exception.AppException;
import com.tek.swiftcarts.exception.ErrorCodes;
import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Read-only reference data (Status, PaymentMethods, ...) that the UI needs to
 * populate dropdowns in "create" forms across every domain.
 *
 * NOTE: /default-user is legacy. Every "create"/"process" endpoint now resolves
 * the acting user itself from the caller's SwiftCartPrincipal (JWT), so the UI
 * no longer needs to send createdBy/entryBy/processedBy back on requests.
 */
@RestController
@Tag(name = "Lookups", description = "Reference data for populating form dropdowns")
@RequestMapping("/api/v1/lookups")
@AllArgsConstructor
public class LookupController {

    private final ProductCategoriesRepository categoriesRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final PaymentsStatusRepository paymentsStatusRepository;
    private final DeliveryTypeRepository deliveryTypeRepository;
    private final UsersRepository usersRepository;

    @GetMapping("/product-categories")
    public ResponseEntity<List<ProductCategoriesDTO>> productCategories() {
        return ResponseEntity.ok(categoriesRepository.findAll().stream().map(c -> {
            ProductCategoriesDTO dto = new ProductCategoriesDTO(
                    c.getId(), c.getName(), c.getDescription(), c.getCreatedAt(), null, c.getUpdatedAt(), null, null);
            return dto;
        }).toList());
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethodsDTO>> paymentMethods() {
        return ResponseEntity.ok(paymentMethodsRepository.findAll().stream().map(m -> {
            PaymentMethodsDTO dto = new PaymentMethodsDTO();
            dto.setId(m.getId());
            dto.setName(m.getName());
            dto.setDescription(m.getDescription());
            return dto;
        }).toList());
    }

    @GetMapping("/payment-statuses")
    public ResponseEntity<List<PaymentStatusDTO>> paymentStatuses() {
        return ResponseEntity.ok(paymentsStatusRepository.findAll().stream().map(s -> {
            PaymentStatusDTO dto = new PaymentStatusDTO();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setDescription(s.getDescription());
            return dto;
        }).toList());
    }

    @GetMapping("/delivery-types")
    public ResponseEntity<List<DeliveryTypeDTO>> deliveryTypes() {
        return ResponseEntity.ok(deliveryTypeRepository.findAll().stream().map(t -> {
            DeliveryTypeDTO dto = new DeliveryTypeDTO();
            dto.setId(t.getId());
            dto.setName(t.getName());
            dto.setDescription(t.getDescription());
            return dto;
        }).toList());
    }


    @GetMapping("/default-user")
    public ResponseEntity<UsersDTO> defaultUser() {
        Users user = usersRepository.findByKeyCloakId(DataSeeder.SYSTEM_KEYCLOAK_ID)
                .orElseThrow(() -> new AppException(ErrorCodes.INTERNAL_ERROR, "System user not seeded"));

        UsersDTO dto = new UsersDTO(
                user.getId(), user.getFirstName(), user.getLastName(), user.getCreatedAt(),
                null, null, user.getUpdatedAt(), null, user.getKeyCloakId(), user.getEmail(), null, user.getPhoneNumber());
        return ResponseEntity.ok(dto);
    }

}
