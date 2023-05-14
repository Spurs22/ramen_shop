package com.repository.product;

import com.DTO.ProductBoard;

import java.util.List;

public interface ProductBoardRepository {
	void createProductPost(ProductBoard productBoard);

	void editPost(ProductBoard productBoard);

	int deletePost(Long memberId, Long productId);

	List<ProductBoard> findPostsByMemberId(Long memberId);

	ProductBoard findPostsByProductId(Long productId);

	List<ProductBoard> findAllPosts();

	void registPicture(Long productId, String path);

	Float getAverageRateByPost(Long productId);

}
