package com.repository.admin;

import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderStatistics;


public interface OrderDetailRepository {

	// 송장번호 입력하기
	void setDeliveryNumber(Long orderId, Long deliveryId);
	
	// 전체 주문내역 확인 >> orderBundle 데이터만 출력 (OrderBundle 내 OrderItem List 출력X)
	List<OrderBundle> findOrderAll(int offset, int size, int statusId);
	
	// 전체 주문내역 확인 >> orderBundle 데이터만 출력 (OrderBundle 내 OrderItem List 출력X) - 검색할 때
	List<OrderBundle> findOrderAll(int offset, int size, String condition, String keyword, int statusId);
	
	// 상세 주문내역 확인 >> orderBundle, orderitem 데이터 출력 (OrderBundle 내 OrderItem List 출력O)
	OrderBundle findOrderDetail(int offset, int size, int statusId, Long orderBundleId);
	
	// 상세 주문내역 확인 >> orderBundle, orderitem 데이터 출력 (OrderBundle 내 OrderItem List 출력O) - 검색할 때
	OrderBundle findOrderDetail(int offset, int size, String condition, String keyword, int statusId, Long orderBundleId);
	
	// 데이터 개수
	int dataCount(int statusId);
	
	// 검색에서의 데이터 개수
	int dataCount(String condition, String keyword, int statusId);
	
	// 상품별 매출 통계
	List<OrderStatistics> salesStatisticsByProduct(int proid) ;
}
