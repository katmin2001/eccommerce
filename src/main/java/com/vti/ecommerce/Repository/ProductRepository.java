package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Boolean existsProductByName(String name);
    @Query(value = "select *\n" +
            "from vti_project.tbl_product p\n" +
            "where p.category_id = :categoryId", nativeQuery = true)
    List<Product> findProductByCategoryId(@Param("categoryId") Long categoryId);
}
