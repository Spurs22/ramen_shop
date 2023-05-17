package com.service.product;

import com.DTO.Member;
import com.repository.product.ProductLikeRepository;

import java.util.List;

public class ProductLikeServiceImpl implements ProductLikeService {

	ProductLikeRepository productLikeRepository;

	public ProductLikeServiceImpl(ProductLikeRepository productLikeRepository) {
		this.productLikeRepository = productLikeRepository;
	}

	@Override
	public void likePost(Long memberId, Long ProductPostId) {

	}

	@Override
	public void cancelLikePost(Long memberId, Long ProductPostId) {

	}

	@Override
	public List<Member> findLikePost(Long memberId) {
		return null;
	}

	@Override
	public Boolean isLike(Long memberId, Long ProductPostId) {
		return null;
	}

	@Override
	public int getCntLikePost(Long memberId) {
		return 0;
	}

	@Override
	public List<Member> findLikePost(Long memberId, int offset, int size) {
		return null;
	}
}
