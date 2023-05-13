package test.repository.product;

import com.DTO.ProductComment;
import com.repository.product.ProductCommentRepository;
import com.repository.product.ProductCommentRepositoryImpl;

public class ProductCommentRepositoryImplTest {
	public static void main(String[] args) {
		ProductCommentRepository productCommentRepository = new ProductCommentRepositoryImpl();

		ProductComment comment = new ProductComment(
				1L,
				2L,
				null,
				4.3,
				null,
				"맛있어요"
		);

		// 댓글 작성
		System.out.println("createComment test");
		productCommentRepository.createComment(comment);


	}
}
