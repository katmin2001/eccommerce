package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.DTO.OrderDTO;
import com.vti.ecommerce.Model.Order;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Repository.OrderItemRepository;
import com.vti.ecommerce.Repository.OrderRepository;
import com.vti.ecommerce.Repository.UserPaymentRepository;
import com.vti.ecommerce.Repository.UserRepository;
import com.vti.ecommerce.Service.OrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final UserPaymentRepository userPaymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, UserPaymentRepository userPaymentRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public ResponseEntity<Result> getAllOrder(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        logger.info("OK");
        return ResponseEntity.ok(new Result("SUCCESS","OK",orderRepository.findAll(pageRequest)));
    }

    @Override
    public ResponseEntity<Result> getOrderDetail(Long orderId) {
       try{
           Order order = orderRepository.findById(orderId).orElseThrow(null);
           OrderDTO orderDTO = new OrderDTO();
           orderDTO.setUser(userRepository.findById(order.getUser_id()).orElseThrow(null));
           orderDTO.setUserPayment(userPaymentRepository.findById(order.getUser_payment_id()).orElseThrow(null));
           orderDTO.setTotalPrice(order.getTotal_price());
           orderDTO.setCreatedDate(order.getCreated_date());
           orderDTO.setStatusShipping(order.getStatus_shipping());
           orderDTO.setOrderItemList(orderItemRepository.findOrderItemsByOrderId(orderId));
           logger.info("SUCCESS");
           return ResponseEntity.ok(new Result("SUCCESS","OK",orderDTO));
       }catch (NullPointerException e){
           logger.error("NOT FOUND");
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND","NOT_FOUND",null));
       }
    }
}
