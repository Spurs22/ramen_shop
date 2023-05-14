package test.repository.product;

import com.DTO.ProductBoard;
import com.repository.product.ProductBoardRepository;

import java.util.List;

public class ProductBoardRepositoryImplTest {
	public static void main(String[] args) {
		ProductBoardRepository productBoardRepository = new com.repository.product.ProductBoardRepositoryImpl();
		ProductBoard productBoard = new ProductBoard(
				7L,
				1L,
				null,
				null,
				"상품 상세 설명입니다.",
				null,
				null,
				4.2f,
				null
		);

		ProductBoard productBoard2 = new ProductBoard(
				8L,
				1L,
				null,
				null,
				"상품 상세 설명입니다.",
				null,
				null,
				5.0f,
				null
		);

		ProductBoard productBoard3 = new ProductBoard(
				9L,
				1L,
				null,
				null,
				"상품 상세 설명입니다.",
				null,
				null,
				4.4f,
				null
		);

		// insert
		System.out.println("productBoard create test");
		productBoardRepository.createProductPost(productBoard);
		productBoardRepository.createProductPost(productBoard2);
		productBoardRepository.createProductPost(productBoard3);


		// 조회
//		System.out.println("productBoard findAll test");
//		List<ProductBoard> allPosts = productBoardRepository.findAllPosts();
//		for (ProductBoard allPost : allPosts) {
//			System.out.println(allPost);
//		}

	}
}
