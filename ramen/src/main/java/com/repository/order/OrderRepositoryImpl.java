package com.repository.order;

import java.util.List;

import com.DTO.Order;

public class OrderRepositoryImpl implements OrderRepository{

	@Override
	public void createOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelOrder(Long orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmOrder(Long orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeliveryNumber(Long orderId, String deliveryNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Order> findOrderByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOrderByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
