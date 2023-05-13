package com.repository.product;


import com.DTO.ProductComment;

import java.util.List;

public interface ProductCommentRepository {
	void createComment(ProductComment productComment);

	void editComment(ProductComment productComment);

	void deleteComment(Long memberId, Long commentId);

	List<ProductComment> findCommentsById(Long memberId);

	List<ProductComment> findCommentsByPostId(Long postId);


}
