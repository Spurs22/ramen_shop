package test.repository.product;

import com.DTO.ProductComment;
import com.repository.product.ProductCommentRepository;
import com.repository.product.ProductCommentRepositoryImpl;

import java.util.List;

public class ProductCommentRepositoryImplTest {
	public static void main(String[] args) {
		ProductCommentRepository productCommentRepository = new ProductCommentRepositoryImpl();

		ProductComment comment = new ProductComment(
				2L,
				1L,
				null,
				4.3,
				null,
				"맛있어요"
		);

		ProductComment comment2 = new ProductComment(
				3L,
				1L,
				null,
				3.3,
				null,
				"그냥 그래요"
		);

		ProductComment commentEdit = new ProductComment(
				2L,
				1L,
				null,
				1.3,
				null,
				"맛없어요"
		);

		// 댓글 작성
		System.out.println("createComment test");
		productCommentRepository.createComment(comment);

		// 두번째 댓글
		productCommentRepository.createComment(comment2);

		// 상품 아이디로 검색
		System.out.println("findByProductId");
		List<ProductComment> commentsByProductId = productCommentRepository.findCommentsByProductId(1L);

		for (ProductComment productComment : commentsByProductId) {
			System.out.println(productComment);
		}

		// 댓글 수정
		System.out.println("댓글 수정 테스트");
		productCommentRepository.editComment(commentEdit);

		// 상품 아이디로 검색
		System.out.println("findByProductId");
		commentsByProductId = productCommentRepository.findCommentsByProductId(1L);

		for (ProductComment productComment : commentsByProductId) {
			System.out.println(productComment);
		}

	}
}
