package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.RecipeBoard;

public interface RecipeBoardService {
	void insertRecipe(RecipeBoard recipeBoard) throws SQLException;

	void updateRecipe(RecipeBoard recipeBoard) throws SQLException;

	void deleteRecipe(Long memberId, Long postId) throws SQLException;

	List<RecipeBoard> readRecipe();

	List<RecipeBoard> readRecipe(String condition, String keyword);
	
	int dataCount();
	
	int dataCount(String condition, String keyword);
	
	void updateHitCount(Long id) throws SQLException;
	
	RecipeBoard readRecipe(Long id);
	
	RecipeBoard preReadRecipe(Long id, String condition, String keyword);
	
	RecipeBoard nextReadRecipe(Long id, String condition, String keyword);

	void registPicture(Long postId, String path);
	
	List<RecipeBoard> findByMemberId(Long memberId);
	
	List<RecipeBoard> findByMemberId(Long memberId, int offset, int size);
	
	List<RecipeBoard> readRecipeByHitCount();
	
	List<RecipeBoard> readRecipeByHitCount(String condition, String keyword);
	
	List<RecipeBoard> readRecipeByLike();
	
	List<RecipeBoard> readRecipeByLike(String condition, String keyword);
}
