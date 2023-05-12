package com.repository.product;


import com.DTO.ProductComment;

import java.util.List;

public class ProductCommentRepositoryImpl implements ProductCommentRepository {

	@Override
	public void createRatingComment(ProductComment productComment) {

	}

	@Override
	public void editComment(ProductComment productComment) {

	}

	@Override
	public void deleteComment(Long memberId, Long commentId) {

	}

	@Override
	public List<ProductComment> findCommentsById(Long memberId) {
		return null;
	}

	@Override
	public List<ProductComment> findCommentsByPostId(Long postId) {
		return null;
	}
}
