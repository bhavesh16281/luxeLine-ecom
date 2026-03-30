package com.bhavesh16281.ecommerce.luxeLine_ecom.controller;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.CartDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Cart;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.CartRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.service.CartService;
import com.bhavesh16281.ecommerce.luxeLine_ecom.util.AuthUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthUtil authUtil;

    @Tag(name = "Cart API's", description = "Endpoints for managing shopping cart")
    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId, @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return new  ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @Tag(name = "Cart API's", description = "Endpoints for managing shopping cart")
    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        List<CartDTO> cartDTOList = cartService.getAllCarts();
        return new ResponseEntity<List<CartDTO>>(cartDTOList,HttpStatus.FOUND);
    }

    @Tag(name = "Cart API's", description = "Endpoints for managing shopping cart")
    @GetMapping("/carts/users/cart")
    public ResponseEntity<CartDTO> getCartById(){

        String emailId = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(emailId);
        Long cartId = cart.getCartId();
        CartDTO cartDto = cartService.getCart(emailId,cartId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @Tag(name = "Cart API's", description = "Endpoints for managing shopping cart")
    @PutMapping("/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId, @PathVariable String operation) {

        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,operation.equalsIgnoreCase("delete") ?-1:1);
        return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.OK);
    }

    @Tag(name = "Cart API's", description = "Endpoints for managing shopping cart")
    @DeleteMapping("/carts/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable
    Long productId) {
        String status = cartService.deleteProductFromCart(cartId,productId);
        return new ResponseEntity<String>(status,HttpStatus.OK);
    }
}
