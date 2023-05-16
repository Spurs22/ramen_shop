package com.service.product;

import com.DTO.ProductComment;
import com.repository.product.ProductCommentRepository;
import com.repository.product.ProductRepository;

import java.util.List;

public class ProductCommentServiceImpl implements ProductCommentService {

	ProductCommentRepository productCommentRepository;

	public ProductCommentServiceImpl(ProductCommentRepository productCommentRepository) {
		this.productCommentRepository = productCommentRepository;
	}

	@Override
	public void createComment(ProductComment productComment) {
		productCommentRepository.createComment(productComment);
	}

	@Override
	public void editComment(ProductComment productComment) {
		productCommentRepository.editComment(productComment);
	}

	@Override
	public void deleteComment(Long memberId, Long commentId) {
		productCommentRepository.deleteComment(memberId, commentId);
	}

	@Override
	public List<ProductComment> findCommentsByMemberId(Long memberId) {
		return productCommentRepository.findCommentsByMemberId(memberId);
	}

	@Override
	public List<ProductComment> findCommentsByProductId(Long productId) {
		return productCommentRepository.findCommentsByProductId(productId);
	}
}
