package com.vti.ecommerce.Repository;

import com.vti.ecommerce.Model.Coupon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_coupon coup\n" +
            "where coup.code = :code", nativeQuery = true)
    Coupon findCouponByCode(@Param("code") String code);
}
