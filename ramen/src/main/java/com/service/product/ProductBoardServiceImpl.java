package main.com.service.product;

import main.com.DTO.ProductBoard;
import main.com.repository.product.ProductBoardRepository;

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
	public int deletePost(Long memberId, Long productId) {
		return productBoardRepository.deletePost(memberId, productId);
	}

	@Override
	public List<ProductBoard> findPostsByMemberId(Long memberId) {
		return productBoardRepository.findPostsByMemberId(memberId);
	}

	@Override
	public ProductBoard findPostsByProductId(Long productId) {
		return productBoardRepository.findPostsByProductId(productId);
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
}
