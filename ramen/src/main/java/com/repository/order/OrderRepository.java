package com.repository.order;

import com.DTO.Order;

import java.util.List;


public interface OrderRepository {

	// 주문 bundle 생성
	long createOrderBundle(Order order);

	// 주문 item 추가
	void createOrderList(Order order);
	
	// 주문취소
	void cancelOrder(Long orderId);

	// 주문상태 변경 - 매개변수:상태(결제완료 - 배송중 - 배송완료 - 주문취소)
	void confirmOrder(Long orderId, String statusName);
	
	// 상품의 가격 구하기( 품목 ) (product 매개변수로 받아서)
	long orderPrice(Long productId);
	
	// sum(finalPrice) >> 전체 가격
	long orderAllPrice(Long orderId);
	
	// 주문완료된 사람중에서 리뷰를 남기지 않은 사람.
}


