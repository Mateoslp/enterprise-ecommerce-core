package com.example.enterprise_ecommerce_core.service;

import com.example.enterprise_ecommerce_core.dto.OrderRequestDTO;
import com.example.enterprise_ecommerce_core.entity.Order;
import com.example.enterprise_ecommerce_core.entity.User;
import com.example.enterprise_ecommerce_core.enums.OrderStatus;
import com.example.enterprise_ecommerce_core.repository.OrderRepository;
import com.example.enterprise_ecommerce_core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}