package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_order_item ordi\n" +
            "where ordi.order_id = :orderId",nativeQuery = true)
    List<OrderItem> findOrderItemsByOrderId(@Param("orderId") Long orderId);
}
