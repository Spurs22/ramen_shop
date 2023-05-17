package com.service.product;

import com.DTO.Member;
import com.DTO.ProductBoard;
import com.repository.product.ProductLikeRepository;

import java.util.List;

public class ProductLikeServiceImpl implements ProductLikeService {

	ProductLikeRepository productLikeRepository;

	public ProductLikeServiceImpl(ProductLikeRepository productLikeRepository) {
		this.productLikeRepository = productLikeRepository;
	}

	@Override
	public void likePost(Long memberId, Long ProductPostId) {
		productLikeRepository.likePost(memberId, ProductPostId);
	}

	@Override
	public void cancelLikePost(Long memberId, Long ProductPostId) {
		productLikeRepository.cancelLikePost(memberId, ProductPostId);
	}

	@Override
	public List<ProductBoard> findLikePost(Long memberId) {
		return productLikeRepository.findLikePostById(memberId);
	}

	@Override
	public Boolean isLike(Long memberId, Long ProductPostId) {
		return productLikeRepository.isLike(memberId, ProductPostId);
	}

	@Override
	public int getCntLikePost(Long memberId) {
		return productLikeRepository.getCntLikePost(memberId);
	}

	@Override
	public List<Member> findLikePost(Long memberId, int offset, int size) {
		return productLikeRepository.findLikePostById(memberId, offset, size);
	}
}
