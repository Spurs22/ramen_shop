package com.repository.order;

import com.DTO.Order;

import java.util.List;


public interface OrderRepository {

	// 주문
	void createOrder(Order order);

	// 주문취소
	void cancelOrder(Long orderId);

	// 주문상태 변경 - 매개변수:상태(결제완료 - 배송중 - 배송완료 - 주문취소)
	void confirmOrder(Long orderId, String statusName);

	// 송장번호 만들기
	void setDeliveryNumber(Long orderId, Long deliveryId);
	
	// 전체 주문내역 확인
	List<Order> findOrderByMemberId();
	
	// 회원 > 주문내역 확인
	List<Order> findOrderByMemberId(Long memberId);

	// 상태별 주문내역 확인
	List<Order> findOrderByMemberId(String statusName);
	
	// 주문번호 > 주문내역 확인
	Order findOrderByOrderId(Long orderId);
	
	// 기간별 매출 통계
	// 상품별 매출 통계
}
