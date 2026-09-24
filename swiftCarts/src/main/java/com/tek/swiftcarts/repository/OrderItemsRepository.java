package com.tek.swiftcarts.repository;

import com.tek.swiftcarts.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,String> {

    @Query("select i.product.id as productId, i.product.name as productName, " +
            "sum(i.quantity) as unitsSold, sum(i.total) as revenue " +
            "from OrderItems i group by i.product.id, i.product.name " +
            "order by sum(i.quantity) desc")
    List<BestSellerRow> findBestSellers();

    interface BestSellerRow {
        String getProductId();
        String getProductName();
        long getUnitsSold();
        BigDecimal getRevenue();
    }
}
