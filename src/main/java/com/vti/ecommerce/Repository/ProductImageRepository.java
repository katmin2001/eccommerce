package com.vti.ecommerce.Repository;



import com.vti.ecommerce.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_product_image pi\n" +
            "where pi.product_id = :productId",nativeQuery = true)
    List<ProductImage> findProductsImagesByProductId(@Param("productId") Long productId);
}
