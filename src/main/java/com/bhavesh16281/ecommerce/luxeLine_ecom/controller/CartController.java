package com.bhavesh16281.ecommerce.luxeLine_ecom.controller;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.CartDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return new  ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }
}
