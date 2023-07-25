package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Product> findProductByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    @Query(value = "select *\n" +
            "from vti_project.tbl_product p\n" +
            "inner join (\n" +
            "select ordi.product_id\n" +
            "from vti_project.tbl_order_item ordi\n" +
            "group by ordi.product_id\n" +
            "order by sum(ordi.quantity) desc limit 5)\n" +
            "as e on p.id = e.product_id",nativeQuery = true)
    List<Product> findProductsBySales();
    @Query(value = "SELECT *\n" +
            "FROM vti_project.tbl_product p\n" +
            "where p.name like %:keyword% \n" +
            "or p.description like %:keyword%",nativeQuery = true)
    Page<Product> findProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
