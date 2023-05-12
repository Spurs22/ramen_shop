package com.repository.cart;

import java.sql.PreparedStatement;
import java.util.List;

import com.DTO.Cart;
import com.util.DBUtil;


public class CartRepositoryImpl implements CartRepository{
	
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
			
		} catch (Exception e) {
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}
	
	// 장바구니 아이템 수정 ( 개수 변경시 )
	@Override
	public void editItem(Long productId, Long memberId, int num) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO cart(product_id, member_id, quantity, created_date )"
					+ " VALUES(?, ?, ?, SYSDATE) "; 
			
		} catch (Exception e) {
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
		return 0;
	}

	// 장바구니 목록조회
	@Override
	public List<Cart> findCartByMemberId(Long memberId) {
		return null;
	}

	
	// 장바구니 ?일 이후 품목 삭제
	
	
	// 장바구니 삭제
	
	/**
	 * 
	 * @param memberId			
	 * @param productId
	 * @return				
	 */
	@Override
	public Cart findCartByCartId(Long memberId, Long productId) {
		return null;
	}



}