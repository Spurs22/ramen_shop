package com.repository.recipe;

import java.util.List;

import com.DTO.Member;

public class RecipeLikeRepositoryImpl implements RecipeLikeRepository {

	@Override
	public void likePost(Long memberId, Long postId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelLikePost(Long memberId, Long postId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Member> findLikeMembers(Long postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findLikePost(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isLike(Long memberId, Long postId) {
		// TODO Auto-generated method stub
		return null;
	}

}
