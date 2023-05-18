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
	public int countLike(Long postId) {
		return recipeLikeRepository.countLike(postId);
	}

	@Override
	public boolean likeRecipePost(Long memberId, Long postId) throws SQLException {

		try {
			Boolean isLike = recipeLikeRepository.isLike(memberId, postId);

			if (isLike) {
				recipeLikeRepository.cancelLikePost(memberId, postId); // 공감취소
			} else {
				recipeLikeRepository.likePost(memberId, postId); // 공강
			}

			return true;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<RecipeBoard> findLikePost(Long memberId, int offset, int size) {
		return recipeLikeRepository.findLikePost(memberId, offset, size);
	}

	@Override
	public int dataCount() {
		return recipeLikeRepository.dataCount();
	}
}
