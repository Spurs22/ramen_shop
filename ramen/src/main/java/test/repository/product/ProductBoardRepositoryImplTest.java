package test.repository.product;

import com.DTO.ProductBoard;
import com.repository.product.ProductBoardRepository;

import java.util.List;

public class ProductBoardRepositoryImplTest {
	public static void main(String[] args) {
		ProductBoardRepository productBoardRepository = new com.repository.product.ProductBoardRepositoryImpl();
		ProductBoard productBoard = new ProductBoard(
				4L,
				1L,
				null,
				"상품 상세 설명입니다.",
				null,
				null,
				4.2f
		);

		// insert
//		System.out.println("productBoard create test");
//		productBoardRepository.createPost(productBoard);


		// 조회
		System.out.println("productBoard findAll test");
		List<ProductBoard> allPosts = productBoardRepository.findAllPosts();
		for (ProductBoard allPost : allPosts) {
			System.out.println(allPost);
		}

	}
}
