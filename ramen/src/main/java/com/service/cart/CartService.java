package com.service.cart;

import java.util.List;

import com.DTO.Cart;
import com.DTO.Product;

public interface CartService {
	
	// [ 장바구니 아이템 생성( 존재하지 않을 시 ) ]
	void createItem(Long productId, Long memberId, int quantity);

	//[ 장바구니 아이템 수정 ( 개수 변경시 ) ]
	void editItem(Long productId, Long memberId, int num);
	
	// 장바구니 아이템 리스트 추가 ( 조합 게시판 ) 
	void createItemList(Long memberId, List<Product> list);
	
	// [ 장바구니 아이템 수정 ( 개수 변경시 ) ]
	void editItemNum(Long productId, Long memberId, int num);

	//[ 상품 개수 구하기 ]
	int getItemCnt(Long productId);
	
	//[ 장바구니의 상품 개수 구하기 ]
	int getCnt(Long memberId, Long productId);

	//[ 장바구니 안의 상품 개수 구하기 ]
	int getCnt(Long memberId);
	
	//[ 장바구니 목록조회 ]
	List<Cart> findCartByMemberId(Long memberId);
	
	//[장바구니 선택한 물품 리스트]
	List<Cart> transferCartList(long memberId, long[] productId);
	
	
	//[ 장바구니 30일 이후 품목 삭제 ]
	void deleteAutoCart();
	
	//[ 장바구니의 물품 삭제 ]
	void deleteCart(Long memberId, Long productId);
	
	
	//[ 장바구니 물품 리스트 삭제 ]
	void deleteCartList(long memberId, long[] nums);
	
	//[ 사용자, 상품의 해당된 cart 구하기 ]
	Cart findCartByCartId(Long memberId, Long productId);
}
