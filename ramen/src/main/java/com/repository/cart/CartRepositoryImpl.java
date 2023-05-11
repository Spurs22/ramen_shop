package com.repository.cart;

import java.util.List;

import com.DTO.Cart;

public class CartRepositoryImpl implements CartRepository {

	@Override
	public void editProduct(Long memberId, Long productId, Long num) {
		
	}

	@Override
	public int getCnt(Long memberId, Long productId) {
		return 0;
	}

	@Override
	public List<Cart> findCartByMemberId(Long memberId) {
		return null;
	}

	@Override
	public Cart findCartByCartId(Long memberId, Long productId) {
		return null;
	}

}
