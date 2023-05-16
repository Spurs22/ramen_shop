package com.service.product;

import com.DTO.ProductComment;

import java.util.List;

public interface ProductCommentService {
	void createComment(ProductComment productComment);

	void editComment(ProductComment productComment);

	void deleteComment(Long memberId, Long commentId);

	List<ProductComment> findCommentsByMemberId(Long memberId);

	List<ProductComment> findCommentsByProductId(Long productId);

}
