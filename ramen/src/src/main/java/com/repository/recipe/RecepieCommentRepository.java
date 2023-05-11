package com.repository.recipe;

import com.DTO.RecipeComment;

import java.util.List;

public interface RecepieCommentRepository {
	void createRatingComment(RecipeComment recipeComment);

	void editComment(RecipeComment recipeComment);

	void deleteComment(Long memberId, Long commentId);

	List<RecipeComment> findCommentsByMemberId(Long memberId);

	List<RecipeComment> findCommentsByPostId(Long RecipePostId);
}
