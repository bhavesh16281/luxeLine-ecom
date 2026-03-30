package com.bhavesh16281.ecommerce.luxeLine_ecom.repositories;

import com.bhavesh16281.ecommerce.luxeLine_ecom.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
