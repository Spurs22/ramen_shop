package com.repository.product;

import com.DTO.ProductComment;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductCommentRepositoryImpl implements ProductCommentRepository {

	private final Connection conn = DBConn.getConnection();

	@Override
	public void createComment(ProductComment productComment) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO PRODUCT_COMMENT (MEMBER_ID, PRODUCT_BOARD_ID, RATING, PRODUCT_BOARD_COMMENT, CREATED_DATE)" +
					" VALUES (?,?,?,?, sysdate)";

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
		}
	}

	@Override
	public void editComment(ProductComment productComment) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE PRODUCT_COMMENT SET RATING = ?, PRODUCT_BOARD_COMMENT = ? WHERE MEMBER_ID = ? AND PRODUCT_BOARD_ID = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, productComment.getRating());
			pstmt.setString(2, productComment.getContent());
			pstmt.setLong(3, productComment.getWriterId());
			pstmt.setLong(4, productComment.getBoardId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	/**
	 * 미구현
	 */
	@Override
	public void deleteComment(Long memberId, Long commentId) {

	}

	@Override
	public List<ProductComment> findCommentsByMemberId(Long memberId) {
		return null;
	}

	@Override
	public List<ProductComment> findCommentsByProductId(Long productId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductComment> result = new ArrayList<>();

		try {
			sql = "SELECT member_id, nickname, rating, product_board_comment, TO_CHAR(pc.created_date, 'yyyy/mm/dd') AS created_date , product_id " +
					"FROM product_comment pc " +
					"JOIN order_item oi on oi.id = pc.order_item_id " +
					"JOIN member m ON m.id = pc.member_id\n" +
					"WHERE product_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(new ProductComment(
						rs.getLong("member_id"),
						rs.getLong("product_id"),
						rs.getString("nickname"),
						rs.getDouble("rating"),
						rs.getString("created_date"),
						rs.getString("product_board_comment")
				));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}
}
