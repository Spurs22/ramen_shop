package com.service.product;


import com.DTO.ProductBoard;
import com.DTO.ProductCategory;

import java.util.List;

public interface ProductBoardService {
	void createProductPost(ProductBoard productBoard);
	void editPost(ProductBoard productBoard);
	int deletePost(Long memberId, Long userRoll, Long productId);
	List<ProductBoard> findPostsByMemberId(Long memberId);
	ProductBoard findPostByProductId(Long productId);
	List<ProductBoard> findAllPosts();
	void registPicture(Long productId, String path);
	Float getAverageRateByPost(Long productId);
	List<ProductBoard> findByCategoryAndKeyword(ProductCategory category, String keyword);
}

