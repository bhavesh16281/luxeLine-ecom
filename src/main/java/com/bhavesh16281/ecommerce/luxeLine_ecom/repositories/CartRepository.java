package com.bhavesh16281.ecommerce.luxeLine_ecom.repositories;

import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c where c.user.email = ?1")
    Cart findCartByEmail(String email);

    @Query("SELECT c FROM Cart c where c.user.email = ?1 and c.id = ?2")
    Cart findCartByEmailAndCartId(String emailId, Long cartId);
}
