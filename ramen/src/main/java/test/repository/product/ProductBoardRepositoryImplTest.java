package test.repository.product;

import com.DTO.Product;
import com.DTO.ProductBoard;
import com.DTO.ProductCategory;
import com.repository.product.ProductBoardRepository;

import java.util.List;

public class ProductBoardRepositoryImplTest {
	public static void main(String[] args) {
		ProductBoardRepository productBoardRepository = new com.repository.product.ProductBoardRepositoryImpl();
		ProductBoard productBoard = new ProductBoard(
				new Product(7L),
				1L,
				null,
				"상품 상세 설명입니다.",
				1600
		);

		ProductBoard productBoard2 = new ProductBoard(
				new Product(15L),
				1L,
				null,
				"상품 상세 설명입니다.",
				1600
		);


		// insert
		System.out.println("productBoard create test");
		productBoardRepository.createProductPost(productBoard);
		productBoardRepository.createProductPost(productBoard2);


		// 조회
//		System.out.println("productBoard findAll test");
//		List<ProductBoard> allPosts = productBoardRepository.findAllPosts();
//		for (ProductBoard allPost : allPosts) {
//			System.out.println(allPost);
//		}

	}
}
