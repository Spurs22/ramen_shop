package com.repository.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.QnA;
import com.util.DBConn;
import com.util.DBUtil;

public class QnaRepositoryImpl implements QnaRepository {
	private Connection conn = DBConn.getConnection();

	@Override
	public void insertQna(QnA dto, String mode) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		long seq;
		
		try {
			sql = "SELECT Qna_seq.NEXTVAL FROM dual ";
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
			
			if(mode.equals("write")) {
				// 글쓰기일 때
				dto.setGroupNum(seq);
				dto.setOrderNo(0);
				dto.setDepth(0);
				dto.setParent(0);
			} else if(mode.equals("reply")) {
				// 답변일때
				updateOrderNo(dto.getGroupNum(), dto.getOrderNo());
				
				dto.setDepth(dto.getDepth() + 1);
				dto.setOrderNo(dto.getOrderNo() + 1);
			}
			
			sql = "INSERT INTO Qna(id, member_id, subject, content, groupNum, depth, orderNo, parent, hit_count, created_date ) "
					+ "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getId());
			pstmt.setLong(2, dto.getMemberId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setLong(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getDepth());
			pstmt.setInt(7, dto.getOrderNo());
			pstmt.setLong(8, dto.getParent());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
	}

	@Override
	public void updateOrderNo(long groupNum, int orderNo) throws SQLException {
		// 답변일 경우 orderNo 변경
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE Qna SET orderNo = orderNo+1 WHERE groupNum = ? AND orderNo > ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, groupNum);
			pstmt.setInt(2, orderNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
	}

	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM Qna ";
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
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
			sql = "SELECT NVL(COUNT(*), 0) FROM Qna q "
					+ "  JOIN member m ON q.member_id = m.id ";
			
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1  ";
			} else if (condition.equals("createdDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(q.created_date, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1 ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
			}

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}

	@Override
	public List<QnA> listQna(int offset, int size) {
		List<QnA> list = new ArrayList<QnA>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("  SELECT q.id, member_id, subject, groupNum, orderNo, depth, hit_count, ");
			sb.append("      TO_CHAR(q.created_date, 'YYYY-MM-DD') created_date ");
			sb.append("  FROM Qna q  ");
			sb.append("  JOIN member m ON q.member_id = m.id ");
			sb.append("  ORDER BY groupNum DESC, orderNo ASC  ");
			sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				QnA dto = new QnA();

				dto.setId(rs.getLong(1));
				dto.setMemberId(rs.getLong(2));
				dto.setSubject(rs.getString(3));
				dto.setGroupNum(rs.getLong(4));
				dto.setOrderNo(rs.getInt(5));
				dto.setDepth(rs.getInt(6));
				dto.setHitCount(rs.getInt(7));
				dto.setCreatedDate(rs.getString(8));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<QnA> listQna(int offset, int size, String condition, String keyword) {
		List<QnA> list = new ArrayList<QnA>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("  SELECT q.id, member_id, subject, groupNum, orderNo, depth, hit_count, ");
			sb.append("      TO_CHAR(q.created_date, 'YYYY-MM-DD') created_date ");
			sb.append("  FORM Qna q  ");
			sb.append("  JOIN member m ON q.member_id = m.id ");

			if (condition.equals("all")) {
				sb.append(" WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else if (condition.equals("createdDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(q.created_date, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY groupNum DESC, orderNo ASC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
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
				QnA dto = new QnA();
				
				dto.setId(rs.getLong(1));
				dto.setMemberId(rs.getLong(2));
				dto.setSubject(rs.getString(3));
				dto.setGroupNum(rs.getLong(4));
				dto.setOrderNo(rs.getInt(5));
				dto.setDepth(rs.getInt(6));
				dto.setHitCount(rs.getInt(7));
				dto.setCreatedDate(rs.getString(8));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
		
	}

	@Override
	public void updateHitCount(long id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE Qna SET hit_count=hit_count+1 WHERE id = ?";
			
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
	public QnA readQna(long id) {
		QnA dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT q.id, member_id, subject, content, q.created_date, hit_count, "
					+ "   groupNum, depth, orderNo, parent "
					+ "   FROM Qna q "
					+ "   JOIN member m ON q.member_id = m.id "
					+ "   WHERE q.id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new QnA();
				
				dto.setId(rs.getLong(1));
				dto.setMemberId(rs.getLong(2));
				dto.setSubject(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setCreatedDate(rs.getString(5));
				dto.setHitCount(rs.getInt(6));
				dto.setGroupNum(rs.getLong(7));
				dto.setDepth(rs.getInt(8));
				dto.setOrderNo(rs.getInt(9));
				dto.setParent(rs.getLong(10));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return dto;
	}

	@Override
	public QnA preReadQna(long groupNum, int orderNo, String condition, String keyword) {
		QnA dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append("  SELECT id, subject ");
				sb.append("  FROM Qna q ");
				sb.append("  JOIN member m ON q.member_id = m.id ");
				sb.append(" WHERE ( (groupNum = ? AND orderNo < ?) OR (groupNum > ?) ) ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("createdDate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(q.created_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY groupNum ASC, orderNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setLong(3, groupNum);
                pstmt.setString(4, keyword);
				if (condition.equals("all")) {
					pstmt.setString(5, keyword);
				}
			} else {
				sb.append("  SELECT id, subject FROM Qna ");
				sb.append(" WHERE (groupNum = ? AND orderNo < ?) OR (groupNum > ?) ");
				sb.append(" ORDER BY groupNum ASC, orderNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setLong(3, groupNum);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new QnA();
				
				dto.setId(rs.getLong("id"));
				dto.setSubject(rs.getString("subject"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return dto;
	}

	@Override
	public QnA nextReadQna(long groupNum, int orderNo, String condition, String keyword) {
		QnA dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append("  SELECT id, subject ");
				sb.append("  FROM Qna q ");
				sb.append("  JOIN member m ON q.member_id = m.id ");
				sb.append(" WHERE ( (groupNum = ? AND orderNo > ?) OR (groupNum < ?) ) ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("createdDate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(q.created_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY groupNum ASC, orderNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setLong(3, groupNum);
                pstmt.setString(4, keyword);
				if (condition.equals("all")) {
					pstmt.setString(5, keyword);
				}
				
			} else {
				sb.append("  SELECT id, subject FROM Qna ");
				sb.append(" WHERE (groupNum = ? AND orderNo > ?) OR (groupNum < ?) ");
				sb.append(" ORDER BY groupNum ASC, orderNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setLong(3, groupNum);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new QnA();
				
				dto.setId(rs.getLong("id"));
				dto.setSubject(rs.getString("subject"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return dto;
	}

	@Override
	public void updateQna(QnA dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE Qna SET subject=?, content=? WHERE id=? AND member_id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setLong(3, dto.getId());
			pstmt.setLong(4, dto.getMemberId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void deleteQna(long id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM Qna WHERE id IN "
					+ "  (SELECT id FROM Qna "
					+ "  START WITH id = ? "
					+ "  CONNECT BY PRIOR id = parent) ";
			
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

}
