package com.bhavesh16281.ecommerce.luxeLine_ecom.repositories;

import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Category;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
