package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.Coupon;
import com.vti.ecommerce.Model.DTO.CouponDTO;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Repository.CouponRepository;
import com.vti.ecommerce.Service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class CouponServiceImpl implements CouponService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public ResponseEntity<Result> getAllCoupon(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK", couponRepository.findAll(pageRequest)));
    }

    @Override
    public ResponseEntity<Result> addCoupon(CouponDTO couponDTO) {
        Coupon coupon = couponRepository.findCouponByCode(couponDTO.getCode());
        if(coupon == null){
            coupon = new Coupon();
            coupon.setCode(couponDTO.getCode());
            coupon.setMaxUsage(couponDTO.getMaxUsage());
            coupon.setDiscount(couponDTO.getDiscount());
            coupon.setType(couponDTO.getType());
            coupon.setCondition(couponDTO.getCondition());
            if(couponDTO.getExpirationDate().compareTo(new Date()) < 0){
                logger.error("CONFLICT DATE");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("CONFLICT DATE","CONFLICT",null));
            }
            else{
                coupon.setExpirationDate(couponDTO.getExpirationDate());
            }
            coupon.setCreated_date(new Date());
            logger.info("ADD COUPON SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",couponRepository.save(coupon)));
        }
        else {
            logger.error("CONFLICT");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("EXIST COUPON","CONFLICT",null));
        }
    }

    @Override
    public ResponseEntity<Result> updateCoupon(CouponDTO couponDTO, Long couponId) {
        try {
            Coupon coupon = couponRepository.findById(couponId).orElseThrow(null);
            if((couponDTO.getCode() != null) && (!couponDTO.getCode().equals(coupon.getCode()))){
                Coupon coupon1 = couponRepository.findCouponByCode(couponDTO.getCode());
                if(coupon1 == null){
                    coupon.setCode(couponDTO.getCode());
                }
                else{
                    logger.error("CONFLICT CODE");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("CONFLICT CODE", "CONFLICT",null));
                }
            }
            if(couponDTO.getDiscount() != null){
                coupon.setDiscount(couponDTO.getDiscount());
            }
            if (couponDTO.getExpirationDate() != null){
                if(couponDTO.getExpirationDate().compareTo(new Date()) < 0){
                    logger.error("CONFLICT DATE");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("CONFLICT DATE","CONFLICT",null));
                }
                else{
                    coupon.setExpirationDate(couponDTO.getExpirationDate());
                }
            }
            if(couponDTO.getMaxUsage() != 0){
                coupon.setMaxUsage(couponDTO.getMaxUsage());
            }
            if(couponDTO.getType() != null){
                coupon.setType(couponDTO.getType());
            }
            if(couponDTO.getCondition() != null){
                coupon.setCondition(couponDTO.getCondition());
            }
            coupon.setUpdate_date(new Date());
            logger.info("UPDATE SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", couponRepository.save(coupon)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND COUPON");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND COUPON","NOT_FOUND", null));
        }
    }
    @Override
    public ResponseEntity<Result> deleteCoupon(Long couponId) {
        try {
            Coupon coupon = couponRepository.findById(couponId).orElseThrow(null);
            coupon.setStatus(false);
            logger.info("DELETE SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", couponRepository.save(coupon)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND COUPON");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND COUPON","NOT_FOUND", null));
        }
    }

    @Override
    public ResponseEntity<Result> activeCoupon(Long couponId) {
        try {
            Coupon coupon = couponRepository.findById(couponId).orElseThrow(null);
            coupon.setStatus(true);
            logger.info("DELETE SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", couponRepository.save(coupon)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND COUPON");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND COUPON","NOT_FOUND", null));
        }
    }
}
