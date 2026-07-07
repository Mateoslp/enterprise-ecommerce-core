package com.example.enterprise_ecommerce_core.repository;

import com.example.enterprise_ecommerce_core.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.user.id = :userId")
    List<Order> findByUserIdWithUser(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o JOIN FETCH o.user")
    List<Order> findAllWithUser();
}