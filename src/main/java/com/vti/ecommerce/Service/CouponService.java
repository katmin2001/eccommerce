package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Coupon;
import com.vti.ecommerce.Model.DTO.CouponDTO;
import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface CouponService {
    ResponseEntity<Result> getAllCoupon(int page, int size);
    ResponseEntity<Result> addCoupon(CouponDTO couponDTO);
    ResponseEntity<Result> updateCoupon(CouponDTO couponDTO, Long couponId);
    ResponseEntity<Result> deleteCoupon(Long couponId);
    ResponseEntity<Result> activeCoupon(Long couponId);

}
