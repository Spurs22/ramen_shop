package com.service.mypage;

import java.util.List;

import com.DTO.RecipeBoard;
import com.repository.product.ProductLikeRepository;
import com.repository.recipe.RecipeLikeRepository;

public class MyPageServiceImpl implements MyPageService {

	RecipeLikeRepository recipeLikeRepository;
	ProductLikeRepository productLikeRepository;
	
	public MyPageServiceImpl(RecipeLikeRepository recipeLikeRepository, ProductLikeRepository productLikeRepository) {
		this.recipeLikeRepository = recipeLikeRepository;
		this.productLikeRepository = productLikeRepository;
	}

	
	@Override
	public List<RecipeBoard> findLikePost(Long memberId) {
		productLikeRepository.findLikePostById(memberId);
		recipeLikeRepository.findLikePost(memberId);
		
		return recipeLikeRepository.findLikePost(memberId);
	}
	
	

}
