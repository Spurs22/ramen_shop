package com.repository.product;
import com.DTO.Product;
import com.DTO.ProductBoard;
import com.DTO.ProductCategory;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBoardRepositoryImpl implements ProductBoardRepository{

	private final Connection conn = DBConn.getConnection();

	@Override
	public void createProductPost(ProductBoard productBoard) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "insert INTO product_board (id, MEMBER_ID, content, PRICE, created_date, hit_count)" +
					" VALUES (?, ?, ?, ?, sysdate, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productBoard.getProduct().getProductId());
			pstmt.setLong(2, productBoard.getWriterId());
			pstmt.setString(3, productBoard.getContent());
			pstmt.setInt(4, productBoard.getPrice());
			pstmt.executeUpdate();

			if (productBoard.getImgList() != null) {
				sql = "INSERT INTO PRODUCT_BOARD_PICTURE (ID, PRODUCT_BOARD_ID, PICTURE_PATH) " +
						"VALUES (PRODUCT_BOARD_PICTURE_SEQ.nextval, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				List<String> imgList = productBoard.getImgList();

				for (String img : imgList) {
					pstmt.setLong(1, productBoard.getProduct().getProductId());
					pstmt.setString(2, img);
					pstmt.executeUpdate();
				}
			}

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
	public void editPost(ProductBoard productBoard) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "UPDATE product_board SET content = ?, PRICE = ? " +
					"WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productBoard.getContent());
			pstmt.setInt(2, productBoard.getPrice());
			pstmt.setLong(3, productBoard.getProduct().getProductId());
			pstmt.executeUpdate();

			// 이미 있던 이미지는?
			// 이미 있던 이미지를 수정한다면?
			// 추가로 이미지를 올린다면?
//			sql = "INSERT INTO PRODUCT_BOARD_PICTURE (ID, PRODUCT_BOARD_ID, PICTURE_PATH) " +
//					"VALUES (PRODUCT_BOARD_PICTURE_SEQ.nextval, ?, ?)";
//			pstmt = conn.prepareStatement(sql);

//			List<String> imgList = productBoard.getImgList();
//
//			for (String img : imgList) {
//				pstmt.setLong(1, productBoard.getProductId());
//				pstmt.setString(2, img);
//				pstmt.executeUpdate();
//			}

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
	public int deletePost(Long memberId, Long postId) {
		return 0;
	}

	@Override
	public List<ProductBoard> findPostsByMemberId(Long memberId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductBoard> result = new ArrayList<>();

		try {
			sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id, p.PICTURE " +
					"FROM PRODUCT_BOARD pb " +
					"LEFT JOIN (SELECT product_board_id, AVG(rating) as rating " +
					"    FROM product_comment " +
					"    GROUP BY product_board_id) " +
					"    cm ON pb.id = cm.product_board_id " +
					"JOIN product p ON pb.id = p.id " +
					"JOIN product_category pc ON p.category_id = pc.id " +
					"WHERE MEMBER_ID = ? " +
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
	public ProductBoard findPostByProductId(Long productId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		ProductBoard result = null;

		try {
			sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id, p.PICTURE " +
					"FROM PRODUCT_BOARD pb " +
					"JOIN product p ON pb.id = p.id " +
					"LEFT JOIN (SELECT oi.product_id, AVG(rating) as rating " +
					"FROM product_comment pc " +
					"JOIN order_item oi on oi.id = order_item_id " +
					"GROUP BY oi.product_id) " +
					"cm ON p.id = cm.product_id " +
					"WHERE pb.id = ? " +
					"ORDER BY ID DESC ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, productId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = getProductBoard(rs);
			}

			DBUtil.closeResource(pstmt, rs);

			sql = "SELECT PICTURE_PATH FROM PRODUCT_BOARD_PICTURE WHERE PRODUCT_BOARD_ID = ? ORDER BY PICTURE_PATH";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, productId);

			rs = pstmt.executeQuery();

			ArrayList<String> imgList = new ArrayList<>();

			while (rs.next()) {
				imgList.add(rs.getString("picture_path"));
			}

			if (result != null) result.setImgList(imgList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}

		return result;
	}

	@Override
	public List<ProductBoard> findAllPosts() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductBoard> result = new ArrayList<>();

		try {
			sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id, p.PICTURE " +
					"FROM PRODUCT_BOARD pb " +
					"JOIN product p ON pb.id = p.id " +
					"LEFT JOIN (SELECT oi.product_id, AVG(rating) as rating " +
					"FROM product_comment pc " +
					"JOIN order_item oi on oi.id = order_item_id " +
					"GROUP BY oi.product_id) " +
					"cm ON p.id = cm.product_id " +
					"ORDER BY CREATED_DATE DESC";
			pstmt = conn.prepareStatement(sql);

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
	public void registPicture(Long postId, String path) {

	}

	@Override
	public Float getAverageRateByPost(Long postId) {
//		String sql = ""
		return null;
	}

	@Override
	public List<ProductBoard> findByCategoryAndKeyword(ProductCategory category, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductBoard> result = new ArrayList<>();

		try {
				sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id, p.PICTURE " +
						"FROM PRODUCT_BOARD pb " +
						"JOIN product p ON pb.id = p.id " +
						"LEFT JOIN (SELECT oi.product_id, AVG(rating) as rating " +
						"FROM product_comment pc " +
						"JOIN order_item oi on oi.id = order_item_id " +
						"GROUP BY oi.product_id) " +
						"cm ON p.id = cm.product_id ";

			// 카테고리가 없을 때
			if (category != null && keyword != null) {
				sql += "WHERE category_id = ? AND INSTR(name, ?) > 0 ";
			} else if (category == null && keyword == null) {

			} else if (category == null) {
				sql += "WHERE INSTR(name, ?) > 0 ";
			} else if (keyword == null) {
				sql += "WHERE category_id = ? ";
			}

			sql += "ORDER BY ID DESC ";

			pstmt = conn.prepareStatement(sql);

			if (category != null && keyword != null) {
				pstmt.setInt(1, category.getValue());
				pstmt.setString(2, keyword);
			} else if (category == null && keyword == null) {

			} else if (category == null) {
				pstmt.setString(1, keyword);
			} else if (keyword == null) {
				pstmt.setInt(1, category.getValue());
			}

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
						rs.getString("picture")
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


