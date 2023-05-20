package com.repository.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Cart;
import com.DTO.Product;
import com.util.DBConn;
import com.util.DBUtil;

public class CartRepositoryImpl implements CartRepository{
	private Connection conn = DBConn.getConnection();
	
	@Override	// 장바구니 아이템 생성( 존재하지 않을 시 )
	public void createItem(Long productId, Long memberId, int quantity) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO cart(product_id, member_id, quantity, created_date )"
					+ " VALUES(?, ?, ?, SYSDATE)"; 
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, productId);
			pstmt.setLong(2, memberId);
			pstmt.setInt(3, quantity);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	
	@Override	// 장바구니 아이템 수정 ( 개수 변경시, 날짜 변경, quantity = 기존양 + num )
	public void editItem(Long productId, Long memberId, int num) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE cart SET quantity=(SELECT quantity From cart WHERE product_id = ? AND member_id = ?) + ? ,"
					+ " created_date = SYSDATE "
					+ " WHERE product_id =? AND member_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, productId);
			pstmt.setLong(2, memberId);
			pstmt.setInt(3, num);
			pstmt.setLong(4, productId);
			pstmt.setLong(5, memberId);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}
	
	@Override	// 장바구니 아이템 수정 ( 개수 변경시, 날짜 변경, quantity = num )
	public void editItemNum(Long productId, Long memberId, int num) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " UPDATE cart SET quantity= ?, "
					+ " created_date = SYSDATE "
					+ " WHERE product_id = ? AND member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setLong(2, productId);
			pstmt.setLong(3, memberId);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}
	
	@Override	// 상품 개수 구하기
	public int getItemCnt(Long productId) {
		int cnt = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT remain_quantity FROM product WHERE id = 5?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, productId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return cnt;
	}
	
	@Override		// 상품 개수 구하기
	public int getCnt(Long memberId, Long productId) {
		int cnt = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT quantity FROM cart WHERE member_id=? AND product_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			pstmt.setLong(2, productId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return cnt;
	}

	
	@Override	// 장바구니의 상품 개수 구하기
	public int getCnt(Long memberId) {
		int cnt = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT count(*) FROM cart WHERE member_id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return cnt;
	}
	
	
	@Override	// 장바구니 목록조회
	public List<Cart> findCartByMemberId(Long memberId) {
		List<Cart> list = new ArrayList<Cart>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT c.product_id, c.member_id, c.quantity, c.created_date, p.picture, pb.price, p.name, p.remain_quantity "
					+ "FROM cart c JOIN product p ON  c.product_id = p.id "
					+ "JOIN product_board pb ON pb.id = p.id "
					+ "WHERE c.member_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Cart cart = new Cart();
				
				cart.setProductId(rs.getLong(1));
				cart.setMemberId(rs.getLong(2));
				cart.setQuantity(rs.getInt(3));
				cart.setCreatedDate(rs.getString(4));
				// cart.setPicture(rs.getString(5));
				cart.setPrice(rs.getLong(6));
				cart.setProductName(rs.getString(7));
				cart.setRemainQuantity(rs.getInt(8));
				
				list.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return list;
	}

	@Override	// 장바구니 선택한 물품 리스트
	public List<Cart> transferCartList(long memberId, long[] productId) {
		List<Cart> list = new ArrayList<Cart>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT c.product_id, c.member_id, c.quantity, c.created_date, p.picture, pb.price, p.name "
					+ " FROM cart c JOIN product p ON  c.product_id = p.id "
					+ " JOIN product_board pb ON pb.id = p.id  "
					+ " WHERE c.product_id IN (";
			for (int i = 0; i < productId.length; i++) {
				sql += "?,";
			}
			sql = sql.substring(0, sql.length() - 1) + ") AND c.member_id=?";

			pstmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < productId.length; i++) {
				pstmt.setLong(i + 1, productId[i]);
			}
			pstmt.setLong(productId.length+1, memberId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Cart cart = new Cart();
				
				cart.setProductId(rs.getLong(1));
				cart.setMemberId(rs.getLong(2));
				cart.setQuantity(rs.getInt(3));
				cart.setCreatedDate(rs.getString(4));
				// cart.setPicture(rs.getString(5));
				cart.setPrice(rs.getLong(6));
				cart.setProductName(rs.getString(7));
				
				list.add(cart);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}
	

	@Override	// 장바구니 30일 이후 품목 삭제
	public void deleteAutoCart() {
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "DELETE FROM cart WHERE created_date + INTERVAL '30' DAY < SYSDATE";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
	}
	
	

	@Override	// 장바구니 삭제
	public void deleteCart(Long memberId, Long productId) {
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM cart WHERE member_id=? AND product_id=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			pstmt.setLong(2, productId);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override	// 장바구니 물품 리스트 삭제  
	public void deleteCartList(long memberId, long[] productId) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM cart WHERE product_id IN (";
			for (int i = 0; i < productId.length; i++) {
				sql += "?,";
			}
			sql = sql.substring(0, sql.length() - 1) + ") AND member_id=?";

			pstmt = conn.prepareStatement(sql);
			
			for (int i = 0; i < productId.length; i++) {
				pstmt.setLong(i + 1, productId[i]);
			}
			pstmt.setLong(productId.length+1, memberId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}
	

	@Override	// 사용자, 상품의 해당된 cart 구하기
	public Cart findCartByCartId(Long memberId, Long productId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		Cart cart = new Cart();
		
		try {
			sql = "SELECT product_id, member_id, quantity, created_date "
					+ "	FROM cart "
					+ " WHERE member_id = ? AND product_id = ?";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setLong(1, memberId);
			pstmt.setLong(2, productId);
			rs = pstmt.executeQuery();
				
			while(rs.next()) {
				cart.setProductId(rs.getLong("product_id"));
				cart.setMemberId(rs.getLong("member_id"));
				cart.setQuantity(rs.getInt("quantity"));
				cart.setCreatedDate(rs.getString("created_date"));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
		return cart;
	}


}