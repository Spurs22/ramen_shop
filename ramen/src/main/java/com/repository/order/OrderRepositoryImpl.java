package com.repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.DTO.Order;
import com.util.DBConn;
import com.util.DBUtil;

public class OrderRepositoryImpl implements OrderRepository{

private Connection conn = DBConn.getConnection();
	
	// 주문
	@Override
	public void createOrder(Order order) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT ALL "
					+ " INTO order_bundle(id, member_id, delivery_id, created_date) VALUES (order_bundle_seq.NEXTVAL, ?, ?, SYSDATE) "
					+ " INTO order_item(id, product_id, order_id, status_id, quantity, price, final_price ) "
					+ " VALUES(order_item_seq.NEXTVAL, ?, order_bundle_seq.CURRVAL, ?, ?, ?, ?)"
					+ " SELECT * FROM dual ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, order.getMemberId());
			pstmt.setLong(2, order.getDeliveryId());
			
			pstmt.setLong(3, order.getProductId());
			pstmt.setLong(4, order.getStatusId());
			pstmt.setLong(5, order.getQuantity());
			pstmt.setLong(6, order.getPrice());
			pstmt.setLong(7, order.getFinalPrice());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	// 주문취소 - status_name를 '주문취소'로 변경
	@Override
	public void cancelOrder(Long orderId) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE order_status SET status_name = '주문취소'  "
					+ "	WHERE EXISTS( SELECT order_id, os.id, status_name  "
					+ "	FROM order_status os JOIN order_item oi ON os.id = oi.status_id "
					+ "	WHERE order_id = ?)" ;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderId);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	// 주문상태 변경 - 매개변수:상태(결제완료 - 배송중 - 배송완료 + 주문취소)
	@Override
	public void confirmOrder(Long orderId, String statusName) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE order_status SET status_name = ?  "
					+ "	WHERE EXISTS( SELECT order_id, os.id, status_name  "
					+ "	FROM order_status os JOIN order_item oi ON os.id = oi.status_id "
					+ "	WHERE order_id = ?)" ;
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, statusName);
			pstmt.setLong(2, orderId);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void setDeliveryNumber(Long orderId, Long deliveryId){
		
	}

	@Override
	public List<Order> findOrderByMemberId(Long memberId) {
		return null;
	}

	@Override
	public Order findOrderByOrderId(Long orderId) {
		return null;
	}

}
