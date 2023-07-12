package com.vti.ecommerce.Repository;

import com.vti.ecommerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_cart c\n" +
            "where c.user_id = :userId", nativeQuery = true)
    Cart findCartByUserId(@Param("userId") Long userId);
}
