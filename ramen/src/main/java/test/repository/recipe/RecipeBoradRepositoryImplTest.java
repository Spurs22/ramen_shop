package test.repository.recipe;

import java.util.ArrayList;
import java.util.List;

import com.DTO.RecipeBoard;
import com.DTO.RecipeProduct;
import com.repository.recipe.RecipeBoardRepository;
import com.repository.recipe.RecipeBoardRepositoryImpl;

public class RecipeBoradRepositoryImplTest {
	public static void main(String[] args) {
		RecipeBoardRepository recipe = new RecipeBoardRepositoryImpl();
		
		RecipeBoard reci = new RecipeBoard();
		reci.setMemberId(1L);
		// reci.setId(5);
		reci.setSubject("제목1");
		reci.setContent("내용1");
		reci.setIpAddress("127.0.0.1");
		
		List<RecipeProduct> list = new ArrayList<>();
		
		
		for(Long i = 1L; i<=2L; i++) {
			RecipeProduct recipeproduct = new RecipeProduct();
			
			recipeproduct.setProductId(i);
			recipeproduct.setQuantity(i.intValue() + 4);
			// recipeboard.setRecipeId(5L);
			
			list.add(recipeproduct);
		}
		
		try {
			recipe.insertRecipe(reci);
			// recipe.updateRecipe(reci);
			// recipe.deleteRecipe(1, 5);
			// recipe.readRecipe();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
