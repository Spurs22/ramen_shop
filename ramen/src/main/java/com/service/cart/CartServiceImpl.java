package com.service.cart;

import java.util.List;

import com.DTO.Cart;
import com.DTO.Product;
import com.repository.cart.CartRepository;


public class CartServiceImpl implements CartService{
	CartRepository cartRepository;
	
	public CartServiceImpl(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	// 해당 품목이 장바구니에 이미 존재하면 createItem, 아니면 editItem
	@Override
	public void createItem(Long productId, Long memberId, int quantity) {
		List<Cart> list = cartRepository.findCartByMemberId(memberId);
		boolean result = true;
		
		for(Cart c: list) {
			// 해당 품목이 이미 존재
			if(c.getProductId() == productId) {
				cartRepository.editItem(productId, memberId, quantity);
				result = false;
				return;
			}
		}
		
		// 해당 품목이 존재하지 않음
		if(result) {
			cartRepository.createItem(productId, memberId, quantity);
		}
	}

	@Override
	public void editItem(Long productId, Long memberId, int num) {
		cartRepository.editItem(productId, memberId, num);
	}

	// 조합 게시판에서 리스트로 가져와서 처리
	@Override
	public void createItemList(Long memberId, List<Product> list) {
		// 보류
	}
	
	@Override
	public void editItemNum(Long productId, Long memberId, int num) {
		cartRepository.editItemNum(productId, memberId, num);
	}

	@Override
	public int getItemCnt(Long productId) {
		return cartRepository.getItemCnt(productId);
	}

	@Override
	public int getCnt(Long memberId, Long productId) {
		return cartRepository.getCnt(memberId, productId);
	}

	@Override
	public int getCnt(Long memberId) {
		return cartRepository.getCnt(memberId);
	}

	// 30일 이후 품목 삭제 > 장바구니 조회
	@Override
	public List<Cart> findCartByMemberId(Long memberId) {
		cartRepository.deleteAutoCart();
		
		return cartRepository.findCartByMemberId(memberId);
	}

	@Override
	public List<Cart> transferCartList(long memberId, long[] productId) {
		return cartRepository.transferCartList(memberId, productId);
	}

	@Override
	public void deleteAutoCart() {
		cartRepository.deleteAutoCart();
	}

	@Override
	public void deleteCart(Long memberId, Long productId) {
		cartRepository.deleteCart(memberId, productId);
	}

	@Override
	public void deleteCartList(long memberId, long[] nums) {
		cartRepository.deleteCartList(memberId, nums);
	}

	@Override
	public Cart findCartByCartId(Long memberId, Long productId) {
		return cartRepository.findCartByCartId(memberId, productId);
	}



}
