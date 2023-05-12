package com.repository.order;

import java.util.List;

import com.DTO.Order;

public class OrderRepositoryImpl implements OrderRepository{

	@Override
	public void createOrder(Order order) {
		
	}

	@Override
	public void cancelOrder(Long orderId) {
		
	}

	@Override
	public void confirmOrder(Long orderId) {
		
	}

	@Override
	public void setDeliveryNumber(Long orderId, String deliveryNumber) {
		
	}

	@Override
	public List<Order> findOrderByMemberId(Long memberId) {
		return null;
	}

	@Override
	public Order findOrderByOrderId(Long orderId) {
		return null;
	}

}
