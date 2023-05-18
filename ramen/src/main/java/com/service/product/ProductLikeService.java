package com.service.product;

import com.DTO.Member;
import com.DTO.ProductBoard;

import java.util.List;

public interface ProductLikeService {

	/**
	 * @param memberId  멤버 아이디
	 * @return          찜 누른 포스트 리스트
	 */
	List<ProductBoard> findLikePostById(Long memberId);

	/**
	 * @param memberId          멤버 아이디
	 * @param ProductPostId     레시피 게시글 아이디
	 * @return                  찜 여부 Boolean으로 반환
	 */
	Boolean isLike(Long memberId, Long ProductPostId);

	int getCntLikePost(Long memberId);

	List<ProductBoard> findLikePostById(Long memberId, int offset, int size);

	Boolean likeProduct(Long memberId, Long productId);
}
