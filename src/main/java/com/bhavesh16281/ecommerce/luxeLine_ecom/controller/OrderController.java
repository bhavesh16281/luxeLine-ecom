package com.bhavesh16281.ecommerce.luxeLine_ecom.controller;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.OrderDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.OrderRequestDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.service.OrderService;
import com.bhavesh16281.ecommerce.luxeLine_ecom.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod, @RequestBody OrderRequestDTO orderRequestDTO) {

        String emailId = authUtil.loggedInEmail();
        OrderDTO orderDto = orderService.placeOrder(emailId,orderRequestDTO.getAddressId(), paymentMethod, orderRequestDTO.getPgName(), orderRequestDTO.getPgPaymentId(), orderRequestDTO.getPgStatus(), orderRequestDTO.getPgResponseManager());

        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }
}
