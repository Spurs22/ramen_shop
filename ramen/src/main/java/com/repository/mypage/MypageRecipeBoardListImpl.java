package com.repository.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.RecipeBoard;
import com.util.DBConn;
import com.util.DBUtil;

public class MypageRecipeBoardListImpl implements MypageRecipeBoardList{
	private Connection conn = DBConn.getConnection();
	
	// 사용자가 작성한 글 목록
	@Override
	public List<RecipeBoard> findByMemberId(Long memberId, int offset, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<RecipeBoard> list = new ArrayList<>();
		RecipeBoard board = null;
		
		try {
			sql = "SELECT r.id, subject, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date,  NVL(recipeLikeCount, 0) recipeLikeCount, rp.picture_path "
					+ "  FROM recipe_board r "
					+ "  JOIN member m ON m.id = r.member_id "
					+ "  LEFT OUTER JOIN recipe_picture rp ON rp.recipe_id = r.id "
					+ "  LEFT OUTER JOIN ( "
					+ "  	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "  	GROUP BY recipe_id "
					+ "  ) bc ON bc.recipe_id = r.id "
					+ "  WHERE r.member_id = ? "
					+ "  ORDER BY r.id DESC"
					+ "  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new RecipeBoard();
				
				board.setId(rs.getLong("id"));
				board.setSubject(rs.getString("subject"));
				board.setHitCount(rs.getInt("hit_count"));
				board.setCreatedDate(rs.getString("created_date"));
				board.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				board.setPicture(rs.getString("picture_path"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	// 데이터 개수
	@Override
	public int dataCount(Long memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM recipe_board WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
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

	// 좋아요 한 데이터 개수
	@Override
	public int likedataCount(Long memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM recipe_like WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
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
	
	// 사용자가 좋아요 한 게시글 리스트
	@Override
	public List<RecipeBoard> findLikePost(Long memberId, int offset, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard board = null;
		List<RecipeBoard> list = new ArrayList<>();
		
		try { // 좋아요 수 작성일 조회수
			sql = "SELECT r.id, subject, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, NVL(recipeLikeCount, 0) recipeLikeCount, rp.picture_path "
					+ " FROM recipe_board r "
					+ " JOIN recipe_like l ON r.id = l.recipe_id "
					+ " LEFT OUTER JOIN recipe_picture rp ON rp.recipe_id = r.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id "
					+ " WHERE l.member_id = ? "
					+ " ORDER BY r.id DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new RecipeBoard();
				
				board.setId(rs.getLong("id"));
				board.setSubject(rs.getString("subject"));
				board.setHitCount(rs.getInt("hit_count"));
				board.setCreatedDate(rs.getString("created_date"));
				board.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				board.setPicture(rs.getString("picture_path"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}



}
