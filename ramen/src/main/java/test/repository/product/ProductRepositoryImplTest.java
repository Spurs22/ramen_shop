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
				ProductCategory.CUP,
				"박상컵라면",
				11,
				null
		);



//		// 상품 생성
		System.out.println("createProduct test");
		productRepository.createProduct(product);
//


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
