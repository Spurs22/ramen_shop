package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.RecipeComment;

public interface RecipeCommentService {
	void createComment(RecipeComment recipeComment) throws SQLException;

	void editComment(RecipeComment recipeComment) throws SQLException;

	void deleteComment(Long memberId, Long commentId) throws SQLException;

	List<RecipeComment> findCommentsByMemberId(Long memberId);

	List<RecipeComment> findCommentsByPostId(Long RecipePostId);
	
	RecipeComment readComment(Long commentId, Long memberId);
	
	int countComment(Long RecipePostId);
}
