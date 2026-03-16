package com.bhavesh16281.ecommerce.luxeLine_ecom.service;

import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.CartDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.dto.ProductDTO;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.APIException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.exceptions.ResourceNotFoundException;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Cart;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.CartItem;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Product;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.CartItemRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.CartRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthUtil authUtil;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(),productId);
        if(cartItem != null){
            throw new APIException("Product "+product.getProductName()+" already exists in the cart");
        }

        if(product.getQuantity() ==0){
            throw new APIException(product.getProductName()+" is not available");
        }

        if(product.getQuantity()<quantity){
            throw new APIException("Please, make an order of the "+product.getProductName()+" less than or equal to the quantity "+product.getQuantity());
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setCart(cart);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+ (product.getSpecialPrice()*quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item -> {ProductDTO map = modelMapper.map(item.getProduct(),ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });
        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart !=null){
            return userCart;
        }

        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        return cartRepository.save(cart);
    }
}
