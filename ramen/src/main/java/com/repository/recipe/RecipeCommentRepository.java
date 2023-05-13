package com.repository.recipe;

import com.DTO.RecipeComment;

import java.sql.SQLException;
import java.util.List;

public interface RecipeCommentRepository {
	void createComment(RecipeComment recipeComment) throws SQLException;

	void editComment(RecipeComment recipeComment) throws SQLException;

	void deleteComment(Long memberId, Long commentId) throws SQLException;

	List<RecipeComment> findCommentsByMemberId(Long memberId);

	List<RecipeComment> findCommentsByPostId(Long RecipePostId);
}
