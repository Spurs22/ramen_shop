package com.repository.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.DTO.ProductBoard;
import com.DTO.RecipeBoard;
import com.util.DBConn;
import com.util.DBUtil;

public class RecepieBoardRepositoryImpl implements RecepieBoardRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void insertRecipe(RecipeBoard recipeboard) throws SQLException {
		// 등록해야 하는 것 : 아이디, 멤버 아이디(id), 생성일, 제목, 내용(상품, 수량), 조회 수, 아이피 주소 
		// 레시피 프로덕트에 상품 수량
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO recipe_board (id, member_id, created_date, subject, content, hit_count, ip_address) "
					+ " VALUES (recipe_board_seq.NEXTVAL, ?, SYSDATE, ?, ?, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeboard.getMember_id());
			pstmt.setString(2, recipeboard.getSubject());
			pstmt.setString(3, recipeboard.getContent());
			pstmt.setString(4, recipeboard.getIp_address());
			
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			for(int i = 0; i < 4; i++) {
				sql = "INSERT INTO recipe_product (recipe_id, product_id, quantity) "
						+ " VALUES (recipe_board.CURRVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, recipeboard.getProduct_id());
				pstmt.setInt(2, recipeboard.getQuantity());
			}
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
	}

	@Override
	public void updateRecipe(RecipeBoard recipeBoard) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRecipe(long memberId, long postId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductBoard> readRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBoard> readRecipe(String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int dataCount() {
		// TODO Auto-generated method stub
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM recipe_board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		// TODO Auto-generated method stub
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}

	@Override
	public RecipeBoard readRecipe(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecipeBoard preReadRecipe(long id, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecipeBoard nextReadRecipe(long id, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registPicture(long postId, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHitCount(long id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE recipe_board SET hit_count = hit_count + 1 WHERE id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
	}

}
