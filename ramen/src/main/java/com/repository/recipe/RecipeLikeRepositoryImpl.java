package com.repository.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.DTO.Member;
import com.util.DBConn;
import com.util.DBUtil;

public class RecipeLikeRepositoryImpl implements RecipeLikeRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void likePost(Long memberId, Long postId) throws SQLException{
		// 레시피 좋아요 테이블에 추가 (게시글 번호, 멤버 아이디)
		PreparedStatement pstmt = null;
		String sql;
		
		if(isLike(memberId, postId) == false) {
			return;
		}
		
		try {
			sql = "INSERT INTO recipe_like (recipe_id, member_id) VALUES (?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			pstmt.setLong(2, memberId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void cancelLikePost(Long memberId, Long postId) throws SQLException {
		// 레시피 좋아요 테이블에 삭제
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM recipe_like WHERE recipe_id = ? AND member_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			pstmt.setLong(2, memberId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
	}

	@Override
	public List<Member> findLikeMembers(Long postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findLikePost(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isLike(Long memberId, Long postId) {
		// TODO Auto-generated method stub
		int check = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) count FROM recipe_like WHERE recipe_id = ? AND member_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			pstmt.setLong(2, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				check = rs.getInt(1);
			}
			
			if(check == 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return false;
	}

	@Override
	public int CountLike(Long postId) {
		// 게시글 당 좋아요 수
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM recipe_like WHERE recipe_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			
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

}
