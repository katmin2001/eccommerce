package com.vti.ecommerce.Repository;


import com.vti.ecommerce.Model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
}
