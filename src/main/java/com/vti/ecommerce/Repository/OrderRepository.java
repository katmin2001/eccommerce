package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_order ord\n" +
            "where ord.user_id = :userId",nativeQuery = true)
    List<Order> findAllByUser(@Param("userId") Long userId);
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_order ord\n" +
            "where ord.user_id = :userId and ord.id = :orderId",nativeQuery = true)
    Order findOrderByUser(@Param("userId") Long userId,
                         @Param("orderId") Long orderId);
}
