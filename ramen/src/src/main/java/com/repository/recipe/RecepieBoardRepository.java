package com.repository.recipe;

import com.DTO.ProductBoard;

import java.util.List;

public interface RecepieBoardRepository {

	Long createPost(ProductBoard productBoard);

	void editPost(ProductBoard productBoard);

	int deletePost(Long memberId, Long postId);

	List<ProductBoard> findPostsByMemberId(Long memberId);

	ProductBoard findPostsByPostId(Long postId);

	List<ProductBoard> findAllPosts();

	void registPicture(Long postId, String path);
}
