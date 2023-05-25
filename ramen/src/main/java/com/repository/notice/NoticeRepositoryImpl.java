package com.repository.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Notice;
import com.util.DBConn;
import com.util.DBUtil;

public class NoticeRepositoryImpl implements NoticeRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void insertNotice(Notice dto) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long seq;
		
		try {
			sql = "SELECT notice_board_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if(rs.next()) {
				seq = rs.getLong(1);
			}
			dto.setId(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO notice_board(id, member_id, subject, content, hit_count, notice, created_date ) "
					+ "  VALUES (?, ?, ?, ?, 0, ?, SYSDATE) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getId());
			pstmt.setLong(2, dto.getMemberId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getNotice());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
	}

	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice_board ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return result;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice_board n "
					+ "  JOIN member m ON n.member_id = m.id ";
			
			if(condition.equals("all")) {
				sql += "  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			
			} else if(condition.equals("createdDate")) {
				keyword = keyword.replaceAll("(\\-|\\.|\\/)", "");
				sql += "  WHERE TO_CHAR(n.created_date, 'YYYYMMDD') = ? ";
				
			} else {
				sql += "  WHERE INSTR(" +condition+ ", ?) >= 1 ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			if(condition.equals("all")) {
				pstmt.setString(2, keyword);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return result;
	}

	@Override
	public List<Notice> listNotice(int offset, int size) {
		List<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("  SELECT n.id, member_id, subject, hit_count, n.created_date ");
			sb.append("  FROM notice_board n JOIN member m ON m.id = n.member_id ");
			sb.append("  ORDER BY id DESC ");
			sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Notice dto = new Notice();
				
				dto.setId(rs.getLong("id"));
				dto.setMemberId(rs.getLong("member_id"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hit_count"));
				dto.setCreatedDate(rs.getString("created_date"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return list;
	}

	@Override
	public List<Notice> listNotice(int offset, int size, String condition, String keyword) {
		List<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT n.id, member_id, subject, hit_count, n.created_date  ");
			sb.append("  FROM notice_board n JOIN member m ON m.id = n.member_id ");
			
			if(condition.equals("all")) {
				sb.append("  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1  ");
				
			} else if (condition.equals("createdDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(n.created_date, 'YYYYMMDD') = ? ");
				
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			
			sb.append("  ORDER BY id DESC ");
			sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Notice dto = new Notice();
				
				dto.setId(rs.getLong("id"));
				dto.setMemberId(rs.getLong("member_id"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hit_count"));
				dto.setCreatedDate(rs.getString("created_date"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return list;
	}

	@Override
	public List<Notice> listNotice() {
		List<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("  SELECT n.id, member_id, subject, hit_count,");
			sb.append("    TO_CHAR(n.created_date, 'YYYY-MM-DD') created_date ");
			sb.append("  FROM notice_board n");
			sb.append("  JOIN member m ON m.id = n.member_id ");
			sb.append("  WHERE notice=1 ");
			sb.append("  ORDER BY id DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Notice dto = new Notice();
				
				dto.setId(rs.getLong("id"));
				dto.setMemberId(rs.getLong("member_id"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hit_count"));
				dto.setCreatedDate(rs.getString("created_date"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return list;
	}

	// 해당 공지글 보기 
	@Override
	public Notice readNotice(Long id) {
		Notice dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT n.id, member_id, subject, content, hit_count, notice, n.created_date "
					+ "  FROM notice_board n "
					+ "  JOIN member m ON m.id = n.member_id "
					+ "  WHERE n.id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new Notice();
				
				dto.setId(rs.getLong("id"));
				dto.setMemberId(rs.getLong("member_id"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hit_count"));
				dto.setNotice(rs.getInt("notice"));
				dto.setCreatedDate(rs.getString("created_date"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return dto;
	}

	@Override
	public Notice preReadNotice(Long id, String condition, String keyword) {
		Notice dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if(keyword != null && keyword.length() != 0) {
				sb.append("  SELECT n.id, subject ");
				sb.append("  FROM notice_board n ");
				sb.append("  JOIN member m ON m.id = n.member_id ");
				sb.append("  WHERE n.id > ?  ");
				
				if(condition.equals("all")) {
					sb.append("  AND  (INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
					
				} else if(condition.equals("createdDate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("  AND  (TO_CHAR(n.created_date, 'YYYYMMDD') = ?) ");
				} else {
					sb.append("  AND  INSTR(" +condition+ ", ?) >= 1 ");
				}
				sb.append("  ORDER BY id ASC");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, id);
				pstmt.setString(2, keyword);
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
				
			} else {
				sb.append("  SELECT n.id, subject FROM notice_board n ");
				sb.append("  WHERE n.id > ? ");
				sb.append("  ORDER BY id ASC");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, id);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new Notice();
				
				dto.setId(rs.getLong("id"));
				dto.setSubject(rs.getString("subject"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return dto;
	}

	@Override
	public Notice nextReadNotice(Long id, String condition, String keyword) {
		Notice dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if(keyword != null && keyword.length() != 0) {
				sb.append("  SELECT n.id, subject ");
				sb.append("  FROM  notice_board n ");
				sb.append("  JOIN member m ON m.id = n.member_id ");
				sb.append("  WHERE (n.id < ?) ");
				
				if(condition.equals("all")) {
					sb.append("  AND (INSTR(subject, ?) >= 1 OR  INSTR(content, ?) >= 1) ");
				} else if (condition.equals("createdDate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("  AND (TO_CHAR(n.created_date, 'YYYYMMDD') = ?) ");
				} else {
					sb.append("  AND  (INSTR(" +condition+ ", ?) >= 1) ");
				}
				sb.append("  ORDER BY id DESC");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, id);
				pstmt.setString(2, keyword);
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append("  SELECT id, subject FROM notice_board ");
				sb.append("  WHERE id < ? ");
				sb.append("  ORDER BY id DESC ");
				sb.append("  FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, id);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new Notice();
				
				dto.setId(rs.getLong("id"));
				dto.setSubject(rs.getString("subject"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return dto;
	}

	@Override
	public void updateHitCount(Long id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "  UPDATE notice_board SET hit_count = hit_count+1 WHERE id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void updateNotice(Notice dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " UPDATE notice_board SET notice=?, subject=?, content=? WHERE id = ? ";
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setLong(4, dto.getId());
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void deleteNotice(Long id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = " DELETE FROM notice_board WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
	}

	@Override
	public void deleteNoticeList(Long[] ids) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM notice_board WHERE id IN (";
			for(int i = 0; i< ids.length; i++) {
				sql += "?,"; 
			}
			sql = sql.substring(0, sql.length() - 1) + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			for(int i =0; i<ids.length; i++) {
				pstmt.setLong(i + 1, ids[i]);
			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

}
