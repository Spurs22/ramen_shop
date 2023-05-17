package com.repository.product;


import com.DTO.*;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductLikeRepositoryImpl implements ProductLikeRepository {

	private final Connection conn = DBConn.getConnection();

	@Override
	public void likePost(Long memberId, Long productId) {
		PreparedStatement pstmt = null;

		String sql;

		try {
			sql = "insert INTO PRODUCT_LIKE (MEMBER_ID, PRODUCT_BOARD_ID) " +
					"VALUES (?, ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, memberId);
			pstmt.setLong(2, productId);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void cancelLikePost(Long memberId, Long productId) {
		PreparedStatement pstmt = null;

		String sql;

		try {
			sql = "DELETE PRODUCT_LIKE " +
					"WHERE MEMBER_ID = ? AND PRODUCT_BOARD_ID = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, memberId);
			pstmt.setLong(2, productId);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public List<ProductBoard> findLikePostById(Long memberId) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductBoard> result = new ArrayList<>();

		try {
			sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id " +
					"FROM PRODUCT_BOARD pb " +
					"JOIN product p ON pb.id = p.id " +
					"LEFT JOIN (SELECT oi.product_id, AVG(rating) as rating " +
					"FROM product_comment pc " +
					"JOIN order_item oi on oi.id = order_item_id " +
					"GROUP BY oi.product_id) " +
					"cm ON p.id = cm.product_id " +
					"WHERE pb.id in (SELECT product_board_id " +
					"FROM product_like " +
					"WHERE member_id = ?) " +
					"ORDER BY ID DESC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(getProductBoard(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public Boolean isLike(Long memberId, Long productId) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		boolean result = false;

		try {
			sql = "SELECT count(*) cnt " +
					"FROM product_like " +
					"WHERE member_id = ? AND product_board_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			pstmt.setLong(2, productId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int cnt = rs.getInt("cnt");

				if (cnt == 1) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public int getCntLikePost(Long memberId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int result = 0;

		try {
			sql = "SELECT count(*) cnt " +
					"FROM product_like " +
					"WHERE MEMBER_ID = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public List<ProductBoard> findLikePostById(Long memberId, int offset, int size) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductBoard> result = new ArrayList<>();

		try {
			sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id " +
					"FROM PRODUCT_BOARD pb " +
					"JOIN product p ON pb.id = p.id " +
					"LEFT JOIN (SELECT oi.product_id, AVG(rating) as rating " +
					"FROM product_comment pc " +
					"JOIN order_item oi on oi.id = order_item_id " +
					"GROUP BY oi.product_id) " +
					"cm ON p.id = cm.product_id " +
					"WHERE pb.id in (SELECT product_board_id " +
					"FROM product_like " +
					"WHERE member_id = ?) " +
					"ORDER BY ID DESC " +
					"OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(getProductBoard(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;

	}

	private static ProductBoard getProductBoard(ResultSet rs) throws SQLException {
		return new ProductBoard(
				new Product(
						rs.getLong("id"),
						ProductCategory.getByValue(rs.getInt("category_id")),
						rs.getString("name"),
						rs.getInt("remain_quantity"),
						null
				),
				rs.getLong("member_id" ),
				null,
				rs.getString("content" ),
				rs.getString("created_date" ),
				rs.getInt("hit_count" ),
				rs.getFloat("rating" ),
				rs.getInt("price")
		);
	}
}
