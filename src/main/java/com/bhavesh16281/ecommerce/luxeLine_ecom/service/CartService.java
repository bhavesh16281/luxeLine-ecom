package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
