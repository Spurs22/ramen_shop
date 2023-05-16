package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.Member;
import com.DTO.RecipeBoard;
import com.repository.recipe.RecipeLikeRepository;

public class RecipeLikeServiceImpl implements RecipeLikeService {

	RecipeLikeRepository recipeLikeRepository;
	
	public RecipeLikeServiceImpl(RecipeLikeRepository recipeLikeRepository) {
		this.recipeLikeRepository = recipeLikeRepository;
	}
	
	@Override
	public void likePost(Long memberId, Long postId) throws SQLException {
		recipeLikeRepository.likePost(memberId, postId);
	}

	@Override
	public void cancelLikePost(Long memberId, Long postId) throws SQLException {
		recipeLikeRepository.cancelLikePost(memberId, postId);
	}

	@Override
	public List<Member> findLikeMembers(Long postId) {
		return recipeLikeRepository.findLikeMembers(postId);
	}

	@Override
	public List<RecipeBoard> findLikePost(Long memberId) {
		return recipeLikeRepository.findLikePost(memberId);
	}

	@Override
	public Boolean isLike(Long memberId, Long postId) {
		return recipeLikeRepository.isLike(memberId, postId);
	}

	@Override
	public int CountLike(Long postId) {
		return recipeLikeRepository.CountLike(postId);
	}
	
}
