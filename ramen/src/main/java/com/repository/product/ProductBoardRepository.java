package com.repository.product;

import com.DTO.ProductBoard;
import com.DTO.ProductCategory;

import java.util.List;

public interface ProductBoardRepository {
	void createProductPost(ProductBoard productBoard);

	void editPost(ProductBoard productBoard);

	int deletePost(Long memberId, Long productId);

	List<ProductBoard> findPostsByMemberId(Long memberId);

	ProductBoard findPostByProductId(Long productId);

	List<ProductBoard> findAllPosts();

	void registPicture(Long productId, String path);

	Float getAverageRateByPost(Long productId);

	// 카테고리 + 키워드로 찾기
	List<ProductBoard> findByCategoryAndKeyword(ProductCategory category, String keyword);

	// 카테고리로 찾기

}
