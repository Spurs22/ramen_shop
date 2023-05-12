package com.repository.product;


import com.DTO.Member;

import java.util.List;

public class ProductLikeRepositoryImpl implements ProductLikeRepository {

	@Override
	public void likePost(Long memberId, Long ProductPostId) {

	}

	@Override
	public void cancelLikePost(Long memberId, Long ProductPostId) {

	}

	@Override
	public List<Member> findLikePost(Long memberId) {
		return null;
	}

	@Override
	public Boolean isLike(Long memberId, Long ProductPostId) {
		return null;
	}
}
