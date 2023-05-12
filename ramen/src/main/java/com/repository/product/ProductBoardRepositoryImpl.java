package com.repository.product;
import com.DTO.ProductBoard;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class ProductBoardRepositoryImpl implements ProductBoardRepository{

	private final Connection conn = DBConn.getConnection();

	@Override
	public void createPost(ProductBoard productBoard) {

		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "";
			pstmt = conn.prepareStatement(sql);
//			pstmt.setLong(1, productBoard.);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void editPost(ProductBoard productBoard) {

	}

	@Override
	public int deletePost(Long memberId, Long postId) {
		return 0;
	}

	@Override
	public List<ProductBoard> findPostsByMemberId(Long memberId) {
		return null;
	}

	@Override
	public ProductBoard findPostsByPostId(Long postId) {
		return null;
	}

	@Override
	public List<ProductBoard> findAllPosts() {
		return null;
	}

	@Override
	public void registPicture(Long postId, String path) {

	}

	@Override
	public Float getAverageRateByPost(Long postId) {
		return null;
	}
}
