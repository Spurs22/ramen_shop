package com.repository.cart;

import com.DTO.Cart;

import java.util.List;

public interface CartRepository {

	/**
	 * @param memberId      사용자 아이디
	 * @param productId     상품 아이디
	 * @param num           개수 (뺄때는 - 입력)
	 */
	// 장바구니 
	void editProduct(Long memberId, Long productId, Long num);

	/**
	 * 
	 * @param memberId  	검색할 사람
	 * @param productId		품목
	 * return				개수
	 */
	int getCnt(Long memberId, Long productId);

	// 장바구니 목록조회
	List<Cart> findCartByMemberId(Long memberId);

	// 장바구니 ?일 이후 품목 삭제
	
	
	// 장바구니 삭제
	
	/**
	 * 
	 * @param memberId			
	 * @param productId
	 * @return				
	 */
	Cart findCartByCartId(Long memberId, Long productId);
}
