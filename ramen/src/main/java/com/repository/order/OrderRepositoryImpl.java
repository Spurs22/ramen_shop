package com.repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;
import com.util.DBConn;
import com.util.DBUtil;


public class OrderRepositoryImpl implements OrderRepository{
	private Connection conn = DBConn.getConnection();
	
	// 주문 bundle 생성
	@Override
	public long createOrderBundle(OrderBundle orderBundle) {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		long order_id = 0L;
		try {
			sql = "INSERT INTO order_bundle(id, member_id, created_date,receive_name,tel,post_num,address1,address2) "
					+ " VALUES (order_bundle_seq.NEXTVAL, ?, SYSDATE, ?, ?, ?, ?,? ) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderBundle.getMemberId());
			pstmt.setString(2, orderBundle.getReceiveName());
			pstmt.setString(3, orderBundle.getTel());
			pstmt.setString(4, orderBundle.getPostNum());
			pstmt.setString(5, orderBundle.getAddress1());
			pstmt.setString(6, orderBundle.getAddress2());
			
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
	public void createOrderList(OrderItem orderItem) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO order_item(id, product_id, order_id, status_id, quantity, price, final_price ) "
					+ " VALUES(order_item_seq.NEXTVAL, ?, ?, 1, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderItem.getProductId());
			pstmt.setLong(2, orderItem.getOrderBundleId());
			pstmt.setInt(3, orderItem.getQuantity());					
			pstmt.setLong(4, orderItem.getPrice());
			pstmt.setLong(5, orderItem.getFinalPrice());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	// 주문취소 - status_id를 4('주문취소')로 변경
	@Override
	public void cancelOrder(Long orderId) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			/*sql = "UPDATE order_item SET status_id = 4  "
					+ "	WHERE EXISTS( SELECT order_id, os.id, status_name  "
					+ "	FROM order_status os JOIN order_item oi ON os.id = oi.status_id "
					+ "	WHERE order_id = ?)" ;
			*/
			sql = "UPDATE order_item SET status_id = 4 "
					+ " WHERE order_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderId);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	//  주문상태 변경 - (1:결제완료 - 2:배송중 - 3:배송완료 - 4:주문취소)
	@Override
	public void confirmOrder(Long orderId, Long statusId) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE order_item SET status_id = ? "
					+ " WHERE order_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, statusId);
			pstmt.setLong(2, orderId);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}
	
	// 상품의 가격 구하기 ( 품목 ) 
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

