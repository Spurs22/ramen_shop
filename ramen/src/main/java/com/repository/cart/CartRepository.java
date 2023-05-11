package com.repository.cart;

import com.DTO.Cart;

import java.util.List;

public interface CartRepository {

	/**
	 * @param memberId      사용자 아이디
	 * @param productId     상품 아이디
	 * @param num           개수 (뺄때는 - 입력)
	 */
	void editProduct(Long memberId, Long productId, Long num);

	void getCnt(Long memberId, Long productId);

	List<Cart> findCartByMemberId(Long memberId);

	Cart findCartByCartId(Long cartId);
}
