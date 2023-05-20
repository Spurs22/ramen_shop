package com.service.order;

import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;

public interface OrderService {
	//[ 주문 bundle, item 생성 ]
	long createOrderBundle(OrderBundle orderBundle,  List<OrderItem> list);
	
	//[ 주문취소 ]
	void cancelOrder(Long orderId);

	//[ 주문상태 변경 - (1:결제완료 - 2:배송중 - 3:배송완료 - 4:주문취소) ]
	void confirmOrder(Long orderId, Long statusId);
	
	//[ 상품의 가격 구하기( 품목 ) ]
	long orderPrice(Long productId);
	
	// [ 전체 가격 ]
	long orderAllPrice(Long orderId);
	
	//[ 주문번호에 해당하는 물품리스트 조회 ]
	List<OrderItem> ListItems(Long orderId); 
}
