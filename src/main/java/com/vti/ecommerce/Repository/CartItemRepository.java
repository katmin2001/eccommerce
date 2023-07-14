package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_cart_item cai\n" +
            "where cai.cart_id = :cartId",nativeQuery = true)
    List<CartItem> findCartItemsByUser(@Param("cartId") Long cartId);
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_cart_item cai\n" +
            "where cai.product_id = :productId", nativeQuery = true)
    CartItem findCartItemByProductId(@Param("productId") Long productId);
}
