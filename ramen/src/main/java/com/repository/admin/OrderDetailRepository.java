package com.repository.admin;

import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderStatistics;


public interface OrderDetailRepository {

	// 송장번호 입력하기
	void setDeliveryNumber(Long orderId, Long deliveryId);
	
	// 전체 주문내역 확인 >> orderBundle 데이터만 출력 (OrderBundle 내 OrderItem List 출력X)
	List<OrderBundle> findOrderAll(int offset, int size, String condition, String keyword, Long StatusId);
	
	// 상세 주문내역 확인 >> orderBundle, orderitem 데이터 출력 (OrderBundle 내 OrderItem List 출력O)
	OrderBundle findOrderDetail(int offset, int size, String condition, String keyword, Long StatusId, Long orderBundleId);
	
	// 데이터 개수
	int dataCount();
	
	// 검색에서의 데이터 개수
	int dataCount(String condition, String keyword);
	
	// 상품별 매출 통계
	List<OrderStatistics>  SalesStatisticsByProduct() ;
	
	// 회원 > 전체 주문내역 확인 >> orderBundle 데이터 출력
	public List<OrderBundle> findOrderAllByMemberId(int offset, int size, Long MemberId);
	
	// 회원 > 상세 주문내역 확인 >> orderBundle + orderitem 데이터 출력
	public OrderBundle findOrderDetailByMemberId(int offset, int size, Long MemberId);

}
