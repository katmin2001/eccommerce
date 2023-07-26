package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<Result> getAllOrder(int page, int size);
    ResponseEntity<Result> getOrderDetail(Long orderId);

}
