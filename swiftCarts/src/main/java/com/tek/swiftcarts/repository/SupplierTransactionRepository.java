package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.SupplierTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierTransactionRepository extends JpaRepository<SupplierTransaction, String> {

    List<SupplierTransaction> findBySupplierIdOrderByCreatedAtDesc(String supplierId);

    List<SupplierTransaction> findAllByOrderByCreatedAtDesc();

    @Query("select t.supplier.id as supplierId, t.supplier.name as supplierName, " +
            "sum(t.amount) as totalSpent, count(t) as transactionCount " +
            "from SupplierTransaction t group by t.supplier.id, t.supplier.name " +
            "order by sum(t.amount) desc")
    List<SupplierSpendRow> findSpendPerSupplier();

    interface SupplierSpendRow {
        String getSupplierId();
        String getSupplierName();
        java.math.BigDecimal getTotalSpent();
        long getTransactionCount();
    }
}
