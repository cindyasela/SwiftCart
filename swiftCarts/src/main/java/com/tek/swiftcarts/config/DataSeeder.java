package com.tek.swiftcarts.config;

import com.tek.swiftcarts.model.*;
import com.tek.swiftcarts.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * spring.jpa.hibernate.ddl-auto=create wipes the schema on every boot, so every
 * lookup/reference table (Status, PaymentMethods, ...) starts empty. Almost every
 * entity in this schema has a mandatory FK to one of these tables, so without this
 * seeder no "create" endpoint can ever succeed. Runs once per boot; guarded by
 * count() checks so it's harmless if ddl-auto ever changes to update/validate.
 */
@Component
@AllArgsConstructor
public class
DataSeeder implements ApplicationRunner {

    // No real auth/Keycloak wiring yet - every "createdBy" on new records falls
    // back to this seeded system user until real login is implemented.
    public static final String SYSTEM_KEYCLOAK_ID = "system-seed-user";

    private static final List<String> STATUS_NAMES = List.of(
            "PENDING", "ACTIVE", "Confirmed", "Completed", "UPDATED", "REMOVED",
            "APPROVED", "REJECTED", "PROCESSED", "CANCELLED", "IN STOCK",
            "LOW STOCK", "OUT OF STOCK", "In Transit", "DELIVERED"
    );

    private final StatusRepository statusRepository;
    private final UsersRepository usersRepository;
    private final PaymentsStatusRepository paymentsStatusRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final ProductCategoriesRepository categoriesRepository;
    private final InventoryMovementRepository movementRepository;
    private final DeliveryTypeRepository deliveryTypeRepository;

    @Override
    public void run(ApplicationArguments args) {
        seedStatuses();
        Users systemUser = seedSystemUser();
        seedPaymentStatuses();
        seedPaymentMethods(systemUser);
        seedProductCategories(systemUser);
        seedInventoryMovements(systemUser);
        seedDeliveryTypes(systemUser);
    }

    private void seedStatuses() {
        if (statusRepository.count() > 0) return;

        for (String name : STATUS_NAMES) {
            Status status = new Status();
            status.setName(name);
            status.setDescription(name);
            statusRepository.save(status);
        }
    }

    private Users seedSystemUser() {
        return usersRepository.findByKeyCloakId(SYSTEM_KEYCLOAK_ID).orElseGet(() -> {
            Status active = statusRepository.findByName("ACTIVE")
                    .orElseThrow(() -> new IllegalStateException("ACTIVE status must be seeded first"));

            Users user = new Users();
            user.setFirstName("System");
            user.setLastName("Admin");
            user.setEmail("system@swiftcarts.local");
            user.setKeyCloakId(SYSTEM_KEYCLOAK_ID);
            user.setStatus(active);
            user.setCreatedAt(LocalDateTime.now());
            return usersRepository.save(user);
        });
    }

    private void seedPaymentStatuses() {
        if (paymentsStatusRepository.count() > 0) return;

        for (String name : List.of("PENDING", "PAID", "FAILED", "REFUNDED")) {
            PaymentStatus status = new PaymentStatus();
            status.setName(name);
            status.setDescription(name);
            paymentsStatusRepository.save(status);
        }
    }

    private void seedPaymentMethods(Users systemUser) {
        if (paymentMethodsRepository.count() > 0) return;

        Status active = statusRepository.findByName("ACTIVE").orElseThrow();

        for (String name : List.of("CASH", "CARD", "MOBILE MONEY")) {
            PaymentMethods method = new PaymentMethods();
            method.setName(name);
            method.setDescription(name);
            method.setCreatedBy(systemUser);
            method.setStatus(active);
            method.setCreatedAt(LocalDateTime.now());
            paymentMethodsRepository.save(method);
        }
    }

    private void seedProductCategories(Users systemUser) {
        if (categoriesRepository.count() > 0) return;

        for (String name : List.of("General", "Electronics", "Apparel", "Beauty", "Footwear", "Food & Drink", "Home")) {
            ProductCategories category = new ProductCategories();
            category.setName(name);
            category.setDescription(name);
            category.setCreatedBy(systemUser);
            category.setCreatedAt(LocalDateTime.now());
            categoriesRepository.save(category);
        }
    }

    private void seedInventoryMovements(Users systemUser) {
        if (movementRepository.count() > 0) return;

        Status active = statusRepository.findByName("ACTIVE").orElseThrow();

        for (String name : List.of("STOCK_IN", "STOCK_OUT", "ADJUSTMENT")) {
            InventoryMovements movement = new InventoryMovements();
            movement.setName(name);
            movement.setDescription(name);
            movement.setReason(name);
            movement.setCreatedBy(systemUser);
            movement.setUpdatedBy(systemUser);
            movement.setStatus(active);
            movement.setCreatedAt(LocalDateTime.now());
            movementRepository.save(movement);
        }
    }

    private void seedDeliveryTypes(Users systemUser) {
        if (deliveryTypeRepository.count() > 0) return;

        Status active = statusRepository.findByName("ACTIVE").orElseThrow();

        for (String name : List.of("STANDARD", "EXPRESS", "PICKUP")) {
            DeliveryType type = new DeliveryType();
            type.setName(name);
            type.setDescription(name);
            type.setCreatedBy(systemUser);
            type.setStatus(active);
            type.setCreatedAt(LocalDateTime.now());
            deliveryTypeRepository.save(type);
        }
    }
}
