package com.repository.cart;

import com.DTO.Cart;

import java.util.List;

public interface CartRepository {
	
	/**
	 * [ 장바구니 아이템 생성( 존재하지 않을 시 ) ]
	 * @param productId		상품 아이디
	 * @param memberId		사용자 아이디
	 * @param quantity		개수
	 */
	void createItem(Long productId, Long memberId, int quantity);

	/**
	 * [ 장바구니 아이템 수정 ( 개수 변경시 ) ]
	 * @param memberId      사용자 아이디
	 * @param productId     상품 아이디
	 * @param num           개수 (뺄때는 - 입력)
	 */
	void editItem(Long productId, Long memberId, int num);

	/**
	 * [ 상품 개수 구하기 ]
	 * @param memberId  	사용자 아이디
	 * @param productId		상품 아이디
	 * return				개수
	 */
	int getCnt(Long memberId, Long productId);

	/**
	 * [ 장바구니 목록조회 ]
	 * @param memberId		사용자 아이디
	 * @return				장바구니 목록
	 */
	List<Cart> findCartByMemberId(Long memberId);

	/**
	 * [ 장바구니 30일 이후 품목 삭제 ]
	 */
	void deleteAutoCart();
	
	/**
	 * [ 장바구니 삭제 ]
	 * @param memberId		사용자 아이디
	 * @param productId		상품 아이디
	 */
	void deleteCart(Long memberId, Long productId);
	
	
	/**
	 * [ 사용자, 상품의 해당된 cart 구하기 ]
	 * @param memberId		사용자 아이디		
	 * @param productId		상품 아이디
	 * @return				
	 */
	Cart findCartByCartId(Long memberId, Long productId);
}