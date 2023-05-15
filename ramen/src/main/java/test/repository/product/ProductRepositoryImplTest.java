package test.repository.product;

import com.DTO.Product;
import com.DTO.ProductCategory;
import com.repository.product.ProductRepository;
import com.repository.product.ProductRepositoryImpl;

public class ProductRepositoryImplTest {
	public static void main(String[] args) {
		ProductRepository productRepository = new ProductRepositoryImpl();

		// 샘플 데이터
		Product product = new Product(
				null,
				ProductCategory.CUP,
				"치즈불닭볶음면",
				1200,
				5,
				null
		);

		Product product2 = new Product(
				null,
				ProductCategory.CUP,
				"꼬꼬면",
				1200,
				2,
				null
		);

		Product productEdit = new Product(
				null,
				ProductCategory.BONGJI,
				"무파마",
				1300,
				5,
				null
		);

//		// 상품 생성
		System.out.println("createProduct test");
		productRepository.createProduct(product);
//

		productRepository.createProduct(product2);
		productRepository.createProduct(productEdit);


		// 상품 찾기
//		System.out.println("findProductByProductId test");
//		Product result = productRepository.findProductByProductId(1L);
//		System.out.println("조회 결과 " + result);
//
//		// 상품 수정
//		System.out.println("editProduct test");
//		productRepository.editProduct(1L, productEdit);
//
//		// 수정된 상품 찾기
//		System.out.println("findProductByProductId test");
//		result = productRepository.findProductByProductId(product.getId());
//		System.out.println("조회 결과 " + result);

	}
}
