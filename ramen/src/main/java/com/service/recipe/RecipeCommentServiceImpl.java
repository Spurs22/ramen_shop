package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.RecipeComment;
import com.repository.recipe.RecipeCommentRepository;

public class RecipeCommentServiceImpl implements RecipeCommentService {

	RecipeCommentRepository recipeCommentRepository;
	
	public RecipeCommentServiceImpl(RecipeCommentRepository recipeCommentRepository) {
		this.recipeCommentRepository = recipeCommentRepository;
	}
	
	@Override
	public void createComment(RecipeComment recipeComment) throws SQLException {
		recipeCommentRepository.createComment(recipeComment);
	}

	@Override
	public void editComment(RecipeComment recipeComment) throws SQLException {
		recipeCommentRepository.editComment(recipeComment);
	}

	@Override
	public void deleteComment(Long memberId, Long commentId) throws SQLException {
		recipeCommentRepository.deleteComment(memberId, commentId);
	}

	@Override
	public List<RecipeComment> findCommentsByMemberId(Long memberId) {
		return recipeCommentRepository.findCommentsByMemberId(memberId);
	}

	@Override
	public List<RecipeComment> findCommentsByPostId(Long RecipePostId) {
		return recipeCommentRepository.findCommentsByPostId(RecipePostId);
	}

	@Override
	public RecipeComment readComment(Long commentId, Long memberId) {
		return recipeCommentRepository.readComment(commentId, memberId);
	}

}
