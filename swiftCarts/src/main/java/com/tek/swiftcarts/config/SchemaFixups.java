package com.tek.swiftcarts.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Inventory.product used to be mapped @OneToOne, which meant a second entry
 * for the same product (i.e. any restock) always failed with a duplicate-key
 * error on the "Products" foreign key column. The mapping has been relaxed to
 * @ManyToOne, but spring.jpa.hibernate.ddl-auto=update never drops existing
 * constraints on its own - so the old one stays in the database, still
 * blocking restocks, until something actually removes it. Do it here instead
 * of requiring manual psql access: safe to run on every boot, IF EXISTS makes
 * it a no-op once the constraint is already gone.
 *
 * NOTE: Inventory.name is deliberately NOT touched here - it stays unique,
 * and InventoryServiceImpl.createInventory appends a timestamp to the
 * caller's label so it never collides.
 */
@Component
@AllArgsConstructor
public class SchemaFixups implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SchemaFixups.class);

    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) {

        String sql = "ALTER TABLE inventory DROP CONSTRAINT IF EXISTS inventory_products_key";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

            log.info("SchemaFixups: legacy inventory_products_key constraint removed (if present)");

        } catch (Exception e) {
            log.warn("SchemaFixups: could not drop the legacy inventory_products_key constraint " +
                    "automatically - if restocking still fails with a duplicate key error on that " +
                    "constraint, drop it manually via psql/pgAdmin", e);
        }
    }
}
