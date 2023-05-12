package com.repository.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Cart;
import com.util.DBConn;
import com.util.DBUtil;


public class CartRepositoryImpl implements CartRepository{
private Connection conn = DBConn.getConnection();
	
	/**
	 * @param memberId      사용자 아이디
	 * @param productId     상품 아이디
	 * @param num           개수 (뺄때는 - 입력)
	 */
	// 장바구니 아이템 생성( 존재하지 않을 시 )
	@Override
	public void createItem(Long productId, Long memberId, int quantity) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO cart(product_id, member_id, quantity, created_date )"
					+ " VALUES(?, ?, ?, SYSDATE) "; 
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
	
	// 장바구니 아이템 수정 ( 개수 변경시, 날짜 변경 )
	@Override
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
	

	/**
	 * 
	 * @param memberId  	검색할 사람
	 * @param productId		품목
	 * return				개수
	 */
	@Override
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

	// 장바구니 목록조회
	@Override
	public List<Cart> findCartByMemberId(Long memberId) {
		List<Cart> list = new ArrayList<Cart>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT product_id, member_id, quantity, created_date "
					+ "	FROM cart "
					+ " WHERE member_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Cart cart = new Cart();
				
				cart.setProductId(rs.getLong("product_id"));
				cart.setMemberId(rs.getLong("member_id"));
				cart.setQuantity(rs.getInt("quantity"));
				cart.setCreatedDate(rs.getString("created_date"));
				
				list.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return list;
	}

	
	// 장바구니 ?일 이후 품목 삭제
	
	// 장바구니 삭제
	@Override
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
	
	/**
	 * 
	 * @param memberId		검색할 사람
	 * @param productId		품목
	 * @return				장바구니 정보			
	 */
	
	@Override
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