package com.service.product;


import com.DTO.Member;
import com.DTO.ProductBoard;
import com.DTO.ProductCategory;
import com.repository.member.MemberRepository;
import com.repository.product.ProductBoardRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductBoardServiceImpl implements ProductBoardService {

	ProductBoardRepository productBoardRepository;

	public ProductBoardServiceImpl(ProductBoardRepository productBoardRepository) {
		this.productBoardRepository = productBoardRepository;
	}

	@Override
	public void createProductPost(ProductBoard productBoard) {
		productBoardRepository.createProductPost(productBoard);
	}

	@Override
	public void editPost(ProductBoard productBoard) {
		productBoardRepository.editPost(productBoard);
	}

	@Override
	public int deletePost(Long memberId, Long userRoll, Long productId) {
		Long writerId = productBoardRepository.findPostByProductId(productId).getWriterId();
		if (userRoll == 1 || writerId.equals(memberId)) {
			productBoardRepository.deletePost(memberId, productId);
		}  else {
			return -1;
		}
		return 0;
	}

	@Override
	public List<ProductBoard> findPostsByMemberId(Long memberId) {
		return productBoardRepository.findPostsByMemberId(memberId);
	}

	@Override
	public ProductBoard findPostByProductId(Long productId) {
		return productBoardRepository.findPostByProductId(productId);
	}

	@Override
	public List<ProductBoard> findAllPosts() {
		return productBoardRepository.findAllPosts();
	}

	@Override
	public void registPicture(Long productId, String path) {
		productBoardRepository.registPicture(productId, path);
	}

	@Override
	public Float getAverageRateByPost(Long productId) {
		return productBoardRepository.getAverageRateByPost(productId);
	}

	@Override
	public List<ProductBoard> findByCategoryAndKeyword(ProductCategory category, String keyword) {
		return productBoardRepository.findByCategoryAndKeyword(category, keyword);
	}
}
