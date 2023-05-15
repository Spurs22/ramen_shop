package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.RecipeBoard;
import com.repository.recipe.RecipeBoardRepository;

public class RecipeBoardServiceImpl implements RecipeBoardService {
	
	RecipeBoardRepository recipeBoardRepository;
	
	public RecipeBoardServiceImpl(RecipeBoardRepository recipeBoardRepository) {
		this.recipeBoardRepository = recipeBoardRepository;
	}

	@Override
	public void insertRecipe(RecipeBoard recipeBoard) throws SQLException {
		recipeBoardRepository.insertRecipe(recipeBoard);
	}

	@Override
	public void updateRecipe(RecipeBoard recipeBoard) throws SQLException {
		recipeBoardRepository.updateRecipe(recipeBoard);
	}

	@Override
	public void deleteRecipe(Long memberId, Long postId) throws SQLException {
		recipeBoardRepository.deleteRecipe(memberId, postId);
	}

	@Override
	public List<RecipeBoard> readRecipe() {
		return recipeBoardRepository.readRecipe();
	}

	@Override
	public List<RecipeBoard> readRecipe(String condition, String keyword) {
		return recipeBoardRepository.readRecipe(condition, keyword);
	}

	@Override
	public int dataCount() {
		return recipeBoardRepository.dataCount();
	}

	@Override
	public int dataCount(String condition, String keyword) {
		return recipeBoardRepository.dataCount(condition, keyword);
	}

	@Override
	public void updateHitCount(Long id) throws SQLException {
		recipeBoardRepository.updateHitCount(id);
	}

	@Override
	public RecipeBoard readRecipe(Long id) {
		return recipeBoardRepository.readRecipe(id);
	}

	@Override
	public RecipeBoard preReadRecipe(Long id, String condition, String keyword) {
		return recipeBoardRepository.preReadRecipe(id, condition, keyword);
	}

	@Override
	public RecipeBoard nextReadRecipe(Long id, String condition, String keyword) {
		return recipeBoardRepository.nextReadRecipe(id, condition, keyword);
	}

	@Override
	public void registPicture(Long postId, String path) {
		recipeBoardRepository.registPicture(postId, path);
	}

	@Override
	public List<RecipeBoard> findByMemberId(Long memberId) {
		return recipeBoardRepository.findByMemberId(memberId);
	}
}
