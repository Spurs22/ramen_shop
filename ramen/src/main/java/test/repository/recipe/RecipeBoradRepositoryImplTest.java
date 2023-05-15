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
		RecipeBoardRepository recipe = new RecipeBoardRepositoryImpl();
		RecipeLikeRepository like = new RecipeLikeRepositoryImpl();
		
		RecipeBoard reci = new RecipeBoard();
		reci.setMemberId(1L);
		// reci.setId(5);
		reci.setSubject("제목2");
		reci.setContent("내용2");
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
			// recipe.insertRecipe(reci); // 레시피 등록
			// recipe.updateRecipe(reci); // 레시피 수정
			// recipe.deleteRecipe(1, 5); // 레시피 삭제
			// recipe.readRecipe(); 
			
			// like.likePost(1L, 7L); // 좋아요 누르기
			// like.cancelLikePost(1L, 7L); // 좋아요 취소
//			int count = like.CountLike(7L); // 좋아요 수
//			System.out.println(count); 
			

//			List<RecipeBoard> list2 = new ArrayList<>(); 
//			list2 = like.findLikePost(1L); // 회원이 좋아요한 게시글
//			for(RecipeBoard board : list2) {
//				System.out.print(board.getId() + "\t");
//				System.out.println(board.getSubject());
//			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
