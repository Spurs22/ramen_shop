package com.repository.product;
import com.DTO.ProductBoard;
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
	public void createPost(ProductBoard productBoard) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn.setAutoCommit(false);

			sql = "insert INTO product_board (id, MEMBER_ID, content, created_date, hit_count)" +
					" VALUES (?, ?, ?, sysdate, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productBoard.getProductId());
			pstmt.setLong(2, productBoard.getWriterId());
			pstmt.setString(3, productBoard.getContent());
			pstmt.executeUpdate();

			if (productBoard.getImgList() != null) {
				sql = "INSERT INTO PRODUCT_BOARD_PICTURE (ID, PRODUCT_BOARD_ID, PICTURE_PATH) " +
						"VALUES (PRODUCT_BOARD_PICTURE_SEQ.nextval, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				List<String> imgList = productBoard.getImgList();

				for (String img : imgList) {
					pstmt.setLong(1, productBoard.getProductId());
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

			sql = "UPDATE product_board SET content = ? " +
					"WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productBoard.getContent());
			pstmt.setLong(2, productBoard.getProductId());
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
			sql = "SELECT id, member_id, content, created_date, hit_count " +
					"FROM PRODUCT_BOARD " +
					"WHERE member_id = ? " +
					"ORDER BY ID DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(new ProductBoard(
						rs.getLong("id" ),
						rs.getLong("member_id"),
						null,
						rs.getString("content" ),
						rs.getString("created_date" ),
						rs.getInt("hit_count" )
				));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public ProductBoard findPostsByPostId(Long postId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		ProductBoard result = null;

		try {
			sql = "SELECT id, member_id, content, created_date, hit_count " +
					"FROM PRODUCT_BOARD " +
					"WHERE id = ? " +
					"ORDER BY ID DESC";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, postId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new ProductBoard(
						rs.getLong("id" ),
						rs.getLong("member_id"),
						null,
						rs.getString("content" ),
						rs.getString("created_date" ),
						rs.getInt("hit_count" )
				);
			}

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
			sql = "SELECT id, member_id, content, created_date, hit_count FROM PRODUCT_BOARD ORDER BY ID DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(new ProductBoard(
						rs.getLong("id" ),
						rs.getLong("member_id"),
						null,
						rs.getString("content" ),
						rs.getString("created_date" ),
						rs.getInt("hit_count" )
				));
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
		return null;
	}
}


