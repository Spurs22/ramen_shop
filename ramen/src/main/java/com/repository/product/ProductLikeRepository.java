package com.repository.product;


import com.DTO.Member;

import java.util.List;

public interface ProductLikeRepository {
	/**
	 *
	 * @param memberId          멤버 아이디
	 * @param ProductPostId     찜 할 누를 포스트 아이디
	 */
	void likePost(Long memberId, Long ProductPostId);

	/**
	 * @param memberId          멤버 아이디
	 * @param ProductPostId     찜 취소 할 포스트 아이디
	 */
	void cancelLikePost(Long memberId, Long ProductPostId);

	/**
	 * @param memberId  멤버 아이디
	 * @return          찜 누른 포스트 리스트
	 */
	List<Member> findLikePost(Long memberId);

	/**
	 * @param memberId          멤버 아이디
	 * @param ProductPostId     레시피 게시글 아이디
	 * @return                  찜 여부 Boolean으로 반환
	 */
	Boolean isLike(Long memberId, Long ProductPostId);
}
