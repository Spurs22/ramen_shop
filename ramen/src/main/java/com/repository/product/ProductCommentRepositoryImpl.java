package main.java.ramen.interfaces.product;

import main.java.ramen.DTO.ProductComment;

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
