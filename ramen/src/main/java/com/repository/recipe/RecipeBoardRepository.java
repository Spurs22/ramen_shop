package com.repository.recipe;

import com.DTO.ProductBoard;
import com.DTO.RecipeBoard;

import java.sql.SQLException;
import java.util.List;

public interface RecipeBoardRepository {

	void insertRecipe(RecipeBoard recipeBoard, List<RecipeBoard> list) throws SQLException;

	void updateRecipe(RecipeBoard recipeBoard, List<RecipeBoard> list) throws SQLException;

	void deleteRecipe(long memberId, long postId) throws SQLException;

	List<ProductBoard> readRecipe();

	List<ProductBoard> readRecipe(String condition, String keyword);
	
	int dataCount();
	
	int dataCount(String condition, String keyword);
	
	void updateHitCount(long id) throws SQLException;
	
	RecipeBoard readRecipe(long id);
	
	RecipeBoard preReadRecipe(long id, String condition, String keyword);
	
	RecipeBoard nextReadRecipe(long id, String condition, String keyword);

	void registPicture(long postId, String path);
}
