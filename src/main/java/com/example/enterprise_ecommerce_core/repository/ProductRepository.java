package com.example.enterprise_ecommerce_core.repository;

import com.example.enterprise_ecommerce_core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}