package com.repository.orderDetail;

import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderStatistics;


public interface OrderDetailRepository {

	// 송장번호 입력하기
	void setDeliveryNumber(Long orderId, Long deliveryId);
	
	// 전체 주문내역 확인 >> orderBundle 데이터만 출력
	List<OrderBundle> findOrderAll();
	
	// 회원 > 주문내역 확인
	List<OrderBundle> findOrderByMemberId(Long memberId);

	// 상태별 주문내역 확인
	List<OrderBundle> findOrderByStatusId(Long orderStatusId);
	
	// 주문번호 > 주문내역 확인
	OrderBundle findOrderByOrderId(Long orderId);
	
	// 데이터 개수
	int dataCount();
	
	// 검색에서의 데이터 개수
	int dataCount(String condition, String keyword);
	
	// 검색에서의 리스트
	List<OrderBundle> listBoard(int offset, int size, String condition, String keyword);
	
	// 기간별 매출 통계
	OrderStatistics SalesStatisticsByPeriod(String createdDate);
	
	// 상품별 매출 통계
	OrderStatistics SalesStatisticsByProduct(Long finalprice, Long productId);
}
