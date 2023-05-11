package com.repository.order;

import com.DTO.Order;

import java.util.List;

public interface OrderRepository {

	void createOrder(Order order);

	void cancelOrder(Long orderId);

	void confirmOrder(Long orderId);

	void setDeliveryNumber(Long orderId, String deliveryNumber);

	List<Order> findOrderByMemberId(Long memberId);

	Order findOrderByOrderId(Long orderId);
}
