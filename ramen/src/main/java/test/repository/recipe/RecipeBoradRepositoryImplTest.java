package test.repository.recipe;

import java.util.ArrayList;
import java.util.List;

import com.DTO.RecipeBoard;
import com.DTO.RecipeProduct;
import com.repository.recipe.RecipeBoardRepository;
import com.repository.recipe.RecipeBoardRepositoryImpl;
import com.repository.recipe.RecipeLikeRepository;
import com.repository.recipe.RecipeLikeRepositoryImpl;

public class RecipeBoradRepositoryImplTest {
	public static void main(String[] args) {
//		RecipeBoardRepository recipe = new RecipeBoardRepositoryImpl();
//		RecipeLikeRepository like = new RecipeLikeRepositoryImpl();
		
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
			// recipe.insertRecipe(reci);
			// recipe.updateRecipe(reci);
			// recipe.deleteRecipe(1, 5);
			// recipe.readRecipe();
			// like.likePost(1L, 7L);
			// like.cancelLikePost(1L, 7L);
//			int count = like.CountLike(7L);
//			System.out.println(count);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
