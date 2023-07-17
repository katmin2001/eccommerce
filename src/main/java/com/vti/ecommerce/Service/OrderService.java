package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<Result> getAllOrder();
    ResponseEntity<Result> getOrderDetail(Long orderId);

}
