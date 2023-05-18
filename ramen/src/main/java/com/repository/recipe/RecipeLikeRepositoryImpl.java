package com.repository.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Member;
import com.DTO.RecipeBoard;
import com.util.DBConn;
import com.util.DBUtil;

public class RecipeLikeRepositoryImpl implements RecipeLikeRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void likePost(Long memberId, Long postId) throws SQLException{
		// 레시피 좋아요 테이블에 추가 (게시글 번호, 멤버 아이디)
		PreparedStatement pstmt = null;
		String sql;
		
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
		// 게시글 좋아요 누른 사람들
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		Member member = null;
		List<Member> list = new ArrayList<>();
		
		try {
			sql = "SELECT r.member_id, nickname FROM recipe_like r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " WHERE recipe_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member = new Member();
				
				member.setMemberId(rs.getLong("member_id"));
				member.setNickName(rs.getString("nickname"));
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> findLikePost(Long memberId) {
		// 사용자가 좋아요 누른 글들
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard board = null;
		List<RecipeBoard> list = new ArrayList<>();
		
		try {
			sql = "SELECT r.id, subject FROM recipe_board r "
					+ " JOIN recipe_like l ON r.id = l.recipe_id "
					+ " WHERE l.member_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new RecipeBoard();
				
				board.setId(rs.getLong("id"));
				board.setSubject(rs.getString("subject"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public Boolean isLike(Long memberId, Long postId) {
		// TODO Auto-generated method stub
		boolean result = false;
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
				if (rs.getInt(1)==1) result = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}

	@Override
	public int countLike(Long postId) {
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
