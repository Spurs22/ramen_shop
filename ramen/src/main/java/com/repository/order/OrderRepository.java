package com.repository.order;

import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;

public interface OrderRepository {

	/**
	 * [ 주문 bundle, item 생성 ]
	 * @param orderBundle	orderBundle
	 * @param list			orderItem 리스트
	 * @return				주문번호 ( orderId )
	 */
	long createOrderBundle(OrderBundle orderBundle,  List<OrderItem> list);

	
	/**
	 * [ 주문취소 ]
	 * @param orderId	주문번호
	 */
	void cancelOrder(Long orderId);

	/**
	 * [ 주문상태 변경 - (1:결제완료 - 2:배송중 - 3:배송완료 - 4:주문취소) ]
	 * @param orderId		주문번호
	 * @param statusName	주문상태
	 */
	void confirmOrder(Long orderId, Long statusId);
	
	/**
	 * [ 상품의 가격 구하기( 품목 ) ]
	 * @param productId	 	상품번호
	 * @return				상품가격
	 */
	long orderPrice(Long productId);
	
	/**
	 * [ 전체 가격 ]
	 * @param orderId		주문번호
	 * @return				전체 가격
	 */
	long orderAllPrice(Long orderId);
	
}


