package com.repository.product;


import com.DTO.ProductComment;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductCommentRepositoryImpl implements ProductCommentRepository {

	private final Connection conn = DBConn.getConnection();

	@Override
	public void createComment(ProductComment productComment) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "INSERT INTO PRODUCT_COMMENT (MEMBER_ID, PRODUCT_BOARD_ID, RATING, PRODUCT_BOARD_COMMENT)" +
					" VALUES (?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productComment.getWriterId());
			pstmt.setLong(2, productComment.getBoardId());
			pstmt.setDouble(3, productComment.getRating());
			pstmt.setString(4, productComment.getContent());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);

			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void editComment(ProductComment productComment) {

	}

	@Override
	public void deleteComment(Long memberId, Long commentId) {

	}

	@Override
	public List<ProductComment> findCommentsById(Long memberId) {
		return null;
	}

	@Override
	public List<ProductComment> findCommentsByPostId(Long postId) {
		return null;
	}
}
