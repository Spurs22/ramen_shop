package com.service.product;

import com.DTO.Member;

import java.util.List;

public interface ProductLikeService {
	
	void likePost(Long memberId, Long ProductPostId);

	void cancelLikePost(Long memberId, Long ProductPostId);

	List<Member> findLikePost(Long memberId);

	Boolean isLike(Long memberId, Long ProductPostId);

	int getCntLikePost(Long memberId);

	List<Member> findLikePost(Long memberId, int offset, int size);
}
