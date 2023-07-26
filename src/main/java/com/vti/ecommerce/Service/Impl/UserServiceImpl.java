package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.*;
import com.vti.ecommerce.Model.DTO.CartDTO;
import com.vti.ecommerce.Model.DTO.OrderDTO;
import com.vti.ecommerce.Model.DTO.UserDTO;
import com.vti.ecommerce.Repository.*;
import com.vti.ecommerce.Service.EmailService;
import com.vti.ecommerce.Service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserPaymentRepository userPaymentRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserPaymentRepository userPaymentRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userPaymentRepository = userPaymentRepository;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<Result> getUserDetail(String username) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",userDTO));
        }catch(NullPointerException e){
            logger.error("NOT FOUND USER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> editInfoUser(UserDTO userDTO,String username) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            if(userDTO.getName() != null){
                user.setName(userDTO.getName());
            }
            if(userDTO.getEmail() != null){
                user.setEmail(userDTO.getEmail());
            }
            if(userDTO.getPhone() != null){
                user.setPhone(userDTO.getPhone());
            }
            if(userDTO.getAddress() != null){
                user.setAddress(userDTO.getAddress());
            }
            user.setUpdate_date(new Date());
            logger.info("UPDATE SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",userRepository.save(user)));
        }catch(NullPointerException e){
            logger.error("NOT FOUND USER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> getAllOrder(String username, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderList = orderRepository.findAllByUser(userRepository.findByUsername(username).get().getId(), pageRequest);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orderList){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setUser(userRepository.findById(order.getUser_id()).orElseThrow(null));
//            orderDTO.setUserPayment(order.getUser_payment_id());
            orderDTO.setTotalPrice(order.getTotal_price());
            orderDTO.setCreatedDate(order.getCreated_date());
            orderDTO.setStatusShipping(orderDTO.getStatusShipping());
            orderDTO.setOrderItemList(orderItemRepository.findOrderItemsByOrderId(order.getId()));
            orderDTOList.add(orderDTO);
        }
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK",orderDTOList));
    }

    @Override
    public ResponseEntity<Result> getOrderDetail(String username, Long orderId) {
//        Order order = orderRepository.findOrderByUser(userRepository.findByUsername(username).get().getId(), orderId);
        try{
            Order order = orderRepository.findById(orderId).orElseThrow(null);
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUser(userRepository.findById(order.getUser_id()).orElseThrow(null));
            orderDTO.setUserPayment(userPaymentRepository.findById(order.getUser_payment_id()).orElseThrow(null));
            orderDTO.setTotalPrice(order.getTotal_price());
            orderDTO.setCreatedDate(order.getCreated_date());
            orderDTO.setStatusShipping(orderDTO.getStatusShipping());
            orderDTO.setOrderItemList(orderItemRepository.findOrderItemsByOrderId(order.getId()));
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",orderDTO));
        }catch (NullPointerException e){
            logger.error("NOT FOUND ORDER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND ORDER","NOT_FOUND",null));
        }
    }
//thanh toan cua user
    @Override
    public ResponseEntity<Result> getAllUserPayment(String username) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            List<UserPayment> userPayments = userPaymentRepository.findUserPaymentsByUserId(user.getId());
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCESS","OK", userPayments));
        }catch(NullPointerException e){
            logger.error("NOT FOUND USER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> getDetailUserPayment(Long userPaymentId) {
        try{
            UserPayment userPayment = userPaymentRepository.findById(userPaymentId).orElseThrow(null);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCESS","OK", userPayment));
        }catch(NullPointerException e){
            logger.error("NOT FOUND USERPAYMENT");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USERPAYMENT","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> addUserPayment(String username, UserPayment userPayment) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            if (userPaymentRepository.findUserPaymentByNumber_card(userPayment.getNumber_card()) != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("USERPAYMENT EXIST","CONFLICT",null));
            }
            userPayment.setUser_id(user.getId());
            userPayment.setCreated_date(new Date());
            logger.info("ADD SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",userPaymentRepository.save(userPayment)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND USER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> updateUserPayment(String username, Long userPaymentId, UserPayment userPaymentReQ) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            UserPayment userPayment = userPaymentRepository.findById(userPaymentId).orElseThrow(null);
            if (userPayment == null){
                logger.error("NOT FOUND USERPAYMENT");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USERPAYMENT","NOT_FOUND",null));
            }
            if (userPaymentRepository.findUserPaymentByNumber_card(userPaymentReQ.getNumber_card()) != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("USERPAYMENT EXIST","CONFLICT",null));
            }
            userPayment.setProvider(userPaymentReQ.getProvider());
            userPayment.setNumber_card(userPaymentReQ.getNumber_card());
            userPayment.setUpdate_date(new Date());
            logger.info("UPDATE SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", userPaymentRepository.save(userPayment)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND USER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }
    @Override
    public ResponseEntity<Result> deleteUserPayment(Long userPaymentId) {
        try{
            UserPayment userPayment = userPaymentRepository.findById(userPaymentId).orElseThrow(null);
            userPaymentRepository.delete(userPayment);
            logger.info("DELETE SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",null));
        }catch(NullPointerException e){
            logger.error("NOT FOUND USER");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }
//quản lý cart
    @Override
    public ResponseEntity<Result> addToCart(Long productId, String username) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(null);
            Cart cart = cartRepository.findCartByUserId(userRepository.findByUsername(username).orElse(null).getId());
            if(cart == null){
                cart = new Cart();
                cart.setUser_id(userRepository.findByUsername(username).orElse(null).getId());
                cart.setCreated_date(new Date());
                cartRepository.save(cart);
            }
//        List<CartItem> cartItems = cartItemRepository.findCartItemsByUser(cart.getId());

            CartItem cartItem = cartItemRepository.findCartItemByProductIdAndUserId(productId, cart.getId());
            if(cartItem == null){
                cartItem = new CartItem();
                cartItem.setCart_id(cart.getId());
                cartItem.setQuantity(1);
                cartItem.setProduct_id(productId);
                cartItem.setCreated_date(new Date());
            }
            else {
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setUpdate_date(new Date());
            }
            logger.info("ADD TO CART SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",cartItemRepository.save(cartItem)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND PRODUCT");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT","NOT_FOUND", null));
        }
    }

    @Override
    public ResponseEntity<Result> getCart(String username) {
//        List<CartDTO> cartDTOS = new ArrayList<>();
        Cart cart = cartRepository.findCartByUserId(userRepository.findByUsername(username).get().getId());
        if(cart == null){
            logger.error("NOT FOUND CART");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CART", "NOT_FOUND",null));
        }
        List<CartItem> cartItems = cartItemRepository.findCartItemsByUser(cart.getId());
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK",cartItems));
    }

    @Override
    public ResponseEntity<Result> removeItemInCart(String username, Long cartItemId) {
        Cart cart = cartRepository.findCartByUserId(userRepository.findByUsername(username).get().getId());
        if(cart == null){
            logger.error("NOT FOUND CART");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CART", "NOT_FOUND",null));
        }
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(null);
        if(cartItem == null){
            logger.error("NOT FOUND CARTITEM");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CARTITEM", "NOT_FOUND",null));
        }
        cartItemRepository.delete(cartItem);
        List<CartItem> cartItems = cartItemRepository.findCartItemsByUser(cart.getId());

        logger.info("REMOVE SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK",cartItems));

    }

    @Override
    public ResponseEntity<Result> updateQuantityProduct(String username, CartItem cartItem, Long cartItemId) {
        Cart cart = cartRepository.findCartByUserId(userRepository.findByUsername(username).get().getId());
        if(cart == null){
            logger.error("NOT FOUND CART");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CART", "NOT_FOUND",null));
        }
        CartItem cartItem1 = cartItemRepository.findById(cartItemId).orElseThrow(null);
        if(cartItem1 == null){
            logger.error("NOT FOUND CARTITEM");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CARTITEM", "NOT_FOUND",null));
        }
        cartItem1.setQuantity(cartItem.getQuantity());
        cartItem1.setUpdate_date(new Date());
        logger.info("ADD QUANTITY SUCCESS");
        return ResponseEntity.ok(new Result("SUCESS","OK",cartItemRepository.save(cartItem1)));
    }

    @Override
    public ResponseEntity<Result> orderProduct(String username, CartDTO cartDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = new ArrayList<>();
        Double total_price = (double) 0;
        Order order = new Order();
        order.setUser_id(userRepository.findByUsername(username).get().getId());
        order.setCreated_date(new Date());
        order.setUser_payment_id(cartDTO.getUserPaymentId());
        order.setStatus_shipping("Đã đặt");
        for(Long cartItemId: cartDTO.getCartItemList()){
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
            Product product = productRepository.findById(cartItem.getProduct_id()).orElseThrow();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct_id(cartItem.getProduct_id());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSub_total(cartItem.getQuantity() * product.getPrice());
            orderItem.setCreated_date(new Date());
            if(product.getAmount() > cartItem.getQuantity()){
                product.setAmount(product.getAmount() - cartItem.getQuantity());
                productRepository.save(product);
            } else if (product.getAmount() == cartItem.getQuantity()) {
                product.setAmount(0);
                product.setStatus(false);
                productRepository.save(product);
            } else{
                logger.error("NOT ENOUGH PRODUCT");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("NOT ENOUGH PRODUCT","CONFLICT", null));
            }
            total_price += orderItem.getSub_total();
            orderItems.add(orderItem);
            cartItems.add(cartItem);
        }
        order.setTotal_price(total_price);
        orderRepository.save(order);
        for(OrderItem orderItem: orderItems){
            orderItem.setOrder_id(order.getId());
        }
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItems);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser(userRepository.findByUsername(username).orElseThrow(null));
        orderDTO.setUserPayment(userPaymentRepository.findById(cartDTO.getUserPaymentId()).orElseThrow(null));
        orderDTO.setTotalPrice(total_price);
        orderDTO.setCreatedDate(new Date());
        orderDTO.setStatusShipping("Đã đặt");
        orderDTO.setOrderItemList(orderItems);
        //gui mail
        User user = userRepository.findByUsername(username).orElseThrow(null);
        emailService.sendMail(order, user);

        logger.info("ORDER SUCCESS");
        return ResponseEntity.ok(new Result("ORDER SUCCESS","OK",orderDTO));
    }
}
