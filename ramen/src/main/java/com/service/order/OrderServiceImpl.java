package com.service.order;

import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;
import com.repository.order.OrderRepository;

public class OrderServiceImpl implements OrderService{
	OrderRepository orderRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@Override
	public long createOrderBundle(OrderBundle orderBundle, List<OrderItem> list) {
		return orderRepository.createOrderBundle(orderBundle, list);
	}

	@Override
	public void cancelOrder(Long orderId) {
		orderRepository.cancelOrder(orderId);
	}

	@Override
	public void confirmOrder(Long orderId, Long statusId) {
		orderRepository.confirmOrder(orderId, statusId);
	}

	@Override
	public long orderPrice(Long productId) {
		return orderRepository.orderPrice(productId);
	}
	
	@Override
	public String orderName(Long productId) {
		return orderRepository.orderName(productId);
	}

	@Override
	public long orderAllPrice(Long orderId) {
		return orderRepository.orderAllPrice(orderId);
	}

	@Override
	public List<OrderItem> ListItems(Long orderId) {
		return orderRepository.ListItems(orderId);
	}

	@Override
	public OrderItem findByOrderItemId(Long orderItemId) {
		return orderRepository.findByOrderItemId(orderItemId);
	}

}
