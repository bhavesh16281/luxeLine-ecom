package com.bhavesh16281.ecommerce.luxeLine_ecom.repositories;

import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
