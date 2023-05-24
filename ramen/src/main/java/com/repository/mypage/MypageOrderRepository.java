package com.repository.mypage;

import java.sql.SQLException;
import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;

public interface MypageOrderRepository {
	// 회원의 데이터 개수
	int dataCount(Long memberId) throws SQLException;
	
	// 회원의 전체 주문 내역 확인 (orderBundle 데이터 출력)
	List<OrderBundle> findOrderAll(Long memberId, int offset, int size) throws SQLException;

	// 회원의 상세 주문 내역 확인 
	List<OrderItem> findOrderDetail(Long orderBundleId) throws SQLException;
}
