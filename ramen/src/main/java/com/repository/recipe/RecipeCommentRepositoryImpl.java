package com.repository.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.RecipeComment;
import com.util.DBConn;
import com.util.DBUtil;

public class RecipeCommentRepositoryImpl implements RecipeCommentRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void createComment(RecipeComment recipeComment) throws SQLException {
		// 레시피 게시글 댓글 작성
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO recipe_comment (id, board_id, member_id, content, created_date) "
					+ " VALUES (recipe_comment_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeComment.getBoardId());
			pstmt.setLong(2, recipeComment.getMemberId());
			pstmt.setString(3, recipeComment.getContent());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void editComment(RecipeComment recipeComment) throws SQLException {
		// 레시피 게시글 댓글 수정
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE recipe_comment SET content = ? WHERE id = ?, member_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, recipeComment.getContent());
			pstmt.setLong(2, recipeComment.getId());
			pstmt.setLong(3, recipeComment.getMemberId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void deleteComment(Long memberId, Long commentId) throws SQLException {
		// 레시피 게시글 댓글 삭제
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM recipe_comment WHERE id = ? AND member_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, commentId);
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
	public List<RecipeComment> findCommentsByMemberId(Long memberId) {
		// 레시피 게시글 댓글 리스트
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeComment> list = new ArrayList<>();
		String sql;
		
		try {
			sql = "SELECT nickname, r.id, board_id, content, r.created_date "
					+ " FROM recipe_comment r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " WHERE member_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RecipeComment recipeComment = new RecipeComment();
				
				recipeComment.setNickname(rs.getString("nickname"));
				recipeComment.setId(rs.getLong("id"));
				recipeComment.setBoardId(rs.getLong("board_id"));
				recipeComment.setCotent(rs.getString("content"));
				recipeComment.setCreatedDate(rs.getString("created_date"));
				
				list.add(recipeComment);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeComment> findCommentsByPostId(Long RecipePostId) {
		// 댓글 리스트
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeComment> list = new ArrayList<>();
		String sql;
		
		try {
			sql = "SELECT nickname, r.id, member_id, content, r.created_date "
					+ " FROM recipe_comment r"
					+ " JOIN member m ON r.member_id = m.id "
					+ " WHERE board_id = ?"
					+ " ORDER BY r.id ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, RecipePostId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RecipeComment recipeComment = new RecipeComment();
				
				recipeComment.setNickname(rs.getString("nickname"));
				recipeComment.setId(rs.getLong("id"));
				recipeComment.setMemberId(rs.getLong("member_id"));
				recipeComment.setCotent(rs.getString("content"));
				recipeComment.setCreatedDate(rs.getString("created_date"));
				
				list.add(recipeComment);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public RecipeComment readComment(Long commentId, Long memberId) {
		// 댓글 정보 불러오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeComment comment = null;
		
		try {
			sql = "";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, commentId);
			pstmt.setLong(2, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				comment = new RecipeComment();
				
				comment.setCotent(rs.getString("content"));
				comment.setBoardId(rs.getLong("board_id"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return comment;
	}

	@Override
	public int countComment(Long RecipePostId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int result = 0;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM recipe_comment WHERE board_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, RecipePostId);
			
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
