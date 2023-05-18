package com.service.product;

import com.DTO.Member;
import com.DTO.ProductBoard;
import com.repository.product.ProductLikeRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductLikeServiceImpl implements ProductLikeService {

	ProductLikeRepository productLikeRepository;

	public ProductLikeServiceImpl(ProductLikeRepository productLikeRepository) {
		this.productLikeRepository = productLikeRepository;
	}

	@Override
	public List<ProductBoard> findLikePostById(Long memberId) {
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
	public List<ProductBoard> findLikePostById(Long memberId, int offset, int size) {
		return productLikeRepository.findLikePostById(memberId, offset, size);
	}

	@Override
	public Boolean likeProduct(Long memberId, Long productId) {

		try {
			Boolean isLike = productLikeRepository.isLike(memberId, productId);

			if (isLike) {
				productLikeRepository.cancelLikePost(memberId, productId); // 공감취소
			} else {
				productLikeRepository.likePost(memberId, productId); // 공강
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
