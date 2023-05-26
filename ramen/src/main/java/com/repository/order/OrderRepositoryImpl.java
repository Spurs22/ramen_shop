package com.repository.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Cart;
import com.DTO.OrderBundle;
import com.DTO.OrderItem;
import com.util.DBConn;
import com.util.DBUtil;


public class OrderRepositoryImpl implements OrderRepository{
	private Connection conn = DBConn.getConnection();
	
	// 주문 bundle, item 생성
	@Override
	public long createOrderBundle(OrderBundle orderBundle, List<OrderItem> list) {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		long order_id = 0L;
		try {
			conn.setAutoCommit(false);
			
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
			
			
			sql = "INSERT INTO order_item(id, product_id, order_id, status_id, quantity, price, final_price ) "
					+ " VALUES(order_item_seq.NEXTVAL, ?, order_bundle_seq.CURRVAL, 1, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			for(OrderItem orderItem : list) {
				pstmt.setLong(1, orderItem.getProductId());
				pstmt.setLong(2, orderItem.getQuantity());
				pstmt.setLong(3, orderItem.getPrice());
				pstmt.setLong(4, orderItem.getFinalPrice());
				
				pstmt.executeUpdate();
			}
			conn.commit();
			
			
			pstmt.close();
			pstmt = null;
			
			sql = "SELECT LAST_NUMBER id FROM ALL_SEQUENCES WHERE SEQUENCE_NAME = 'ORDER_BUNDLE_SEQ'";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				order_id = rs.getLong(1)-1;
			}
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}
		return order_id;
	}

	// 주문취소 - status_id를 4('주문취소')로 변경
	@Override
	public void cancelOrder(Long orderId) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			
			
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
	
	@Override // 상품 이름 구하기
	public String orderName(Long productId) {
		String productName = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT name from product p "
					+ " JOIN product_board pb ON p.id = pb.id "
					+ " WHERE p.id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, productId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productName = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return productName;
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

	// 주문번호에 해당하는 물품리스트 조회
	@Override
	public List<OrderItem> ListItems(Long orderId) {
		List<OrderItem> list = new ArrayList<OrderItem>();;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT oi.id, oi.product_id, order_id, status_id, quantity, price, name, p.picture  "
					+ " FROM order_item oi JOIN product p ON p.id = oi.product_id "
					+ " WHERE order_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderItemId(rs.getLong(1));
				orderItem.setProductId(rs.getLong(2));
				orderItem.setOrderBundleId(rs.getLong(3));
				orderItem.setStatusId(rs.getLong(4));
				orderItem.setQuantity(rs.getInt(5));
				orderItem.setPrice(rs.getLong(6));
				orderItem.setProductName(rs.getString(7));
				orderItem.setPicture(rs.getString(8));
				
				list.add(orderItem);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	// [주문아이템 번호에 해당하는 아이템 조회]
	@Override
	public OrderItem findByOrderItemId(Long orderItemId) {
		OrderItem orderItem = new OrderItem();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT id, product_id, order_id, status_id, quantity, price, final_price "
					+ " FROM order_item WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderItemId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderItem = new OrderItem();
				orderItem.setOrderItemId(rs.getLong(1));
				orderItem.setProductId(rs.getLong(2));
				orderItem.setOrderBundleId(rs.getLong(3));
				orderItem.setStatusId(rs.getLong(4));
				orderItem.setQuantity(rs.getInt(5));
				orderItem.setPrice(rs.getLong(6));
				orderItem.setFinalPrice(rs.getLong(7));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return orderItem;
	}



}

