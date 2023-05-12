package com.test.repository.recipe;

import java.util.ArrayList;
import java.util.List;

import com.DTO.RecipeBoard;
import com.repository.recipe.RecipeBoardRepository;
import com.repository.recipe.RecipeBoardRepositoryImpl;

public class RecipeBoradRepositoryImplTest {
	public static void main(String[] args) {
		RecipeBoardRepository recipe = new RecipeBoardRepositoryImpl();
		
		RecipeBoard reci = new RecipeBoard();
		reci.setMember_id(1);
		reci.setSubject("제목1");
		reci.setContent("내용1");
		reci.setIp_address("127.0.0.1");
		
		List<RecipeBoard> list = new ArrayList<>();
		
		RecipeBoard recipeboard = new RecipeBoard();
		
		recipeboard.setProduct_id(1);
		recipeboard.setQuantity(2);
		
		list.add(recipeboard);
		try {
			recipe.insertRecipe(reci, list);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
