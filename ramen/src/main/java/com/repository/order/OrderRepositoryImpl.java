package com.repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DTO.Order;
import com.util.DBConn;
import com.util.DBUtil;


public class OrderRepositoryImpl implements OrderRepository{
	private Connection conn = DBConn.getConnection();
	
	// 주문 bundle 생성
	@Override
	public long createOrderBundle(Order order) {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		long order_id = 0L;
		try {
			sql = "INSERT INTO order_bundle(id, member_id, created_date,receive_name,tel,post_num,address1,address2) "
					+ " VALUES (order_bundle_seq.NEXTVAL, ?, SYSDATE, ?, ?, ?, ?,? ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, order.getMemberId());
			pstmt.setString(2, order.getReceiveName());
			pstmt.setString(3, order.getTel());
			pstmt.setString(4, order.getPostNum());
			pstmt.setString(5, order.getAddress1());
			pstmt.setString(6, order.getAddress2());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "SELECT LAST_NUMBER id FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = 'ORDER_BUNDLE_SEQ'";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				order_id = rs.getLong(1)-1;
				System.out.println(order_id);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
		return order_id;
	}
	
	// 주문 item 추가
	@Override
	public void createOrderList(Order order) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO order_item(id, product_id, order_id, status_id, quantity, price, final_price ) "
					+ " VALUES(order_item_seq.NEXTVAL, ?, ?, 1, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, order.getProductId());
			pstmt.setLong(2, order.getOrderId());
			pstmt.setInt(3, order.getQuantity());					
			pstmt.setLong(4, order.getPrice());
			pstmt.setLong(5, order.getFinalPrice());
			
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
	
	// 상품의 가격 구하기 ( 품목 ) (product 매개변수로 받아서)
	@Override
	public long orderPrice(Long productId) {
		long price = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT price FROM product_board "
					+ " WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, productId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				price = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return price;
	}

	
	
	// sum(finalPrice) >> 전체 가격
	@Override
	public long orderAllPrice(Long orderId) {
		long totalPrice = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT sum(price) totalPrice FROM order_item "
					+ " WHERE order_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalPrice = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return totalPrice;
	}

}

