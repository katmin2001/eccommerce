package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_user_payment upay\n" +
            "where upay.user_id = :userId",nativeQuery = true)
    List<UserPayment> findUserPaymentsByUserId(@Param("userId") Long userId);
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_user_payment upay\n" +
            "where upay.number_card = :numberCard",nativeQuery = true)
    UserPayment findUserPaymentByNumber_card(String numberCard);
    @Query(value = "SELECT * \n" +
            "FROM vti_project.tbl_user_payment upay\n" +
            "where upay.user_id = :userId and upay.number_card = :userPaymentId",nativeQuery = true)
    UserPayment findUserPaymentByUserIdAndUPId(@Param("userId") Long userId,
                                               @Param("userPaymentId") Long userPaymentId);
}
