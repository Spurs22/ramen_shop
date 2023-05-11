package com.repository.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.RecipeComment;

public class RecepieCommentRepositoryImpl implements RecepieCommentRepository {

	@Override
	public void createRatingComment(RecipeComment recipeComment) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editComment(RecipeComment recipeComment) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComment(Long memberId, Long commentId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RecipeComment> findCommentsByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeComment> findCommentsByPostId(Long RecipePostId) {
		// TODO Auto-generated method stub
		return null;
	}

}
