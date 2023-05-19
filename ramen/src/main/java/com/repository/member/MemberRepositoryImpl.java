package com.repository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Member;
import com.util.DBConn;
import com.util.DBUtil;
// 라멘 
public class MemberRepositoryImpl implements MemberRepository {

	private Connection conn = DBConn.getConnection();

	// 로그인
	@Override
	public Member loginMember(String email, String password) throws SQLException {

		Member dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = "SELECT id, nickname,email,roll " 
					+ " FROM member WHERE email=? AND password=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 로그인 성공 처리

				dto = new Member();

				dto.setMemberId(rs.getLong("id"));
				dto.setEmail(rs.getString("email"));
				dto.setNickName(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
				dto.setRoll(rs.getLong("roll"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;

		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return dto;

	}

	@Override
	public Member insertMember(Member member) throws SQLException {

		// 회원가입
		PreparedStatement pstmt = null;
		String sql;
		try {
			conn.setAutoCommit(false);

			sql = "INSERT INTO member(id,name,nickname,password,email,tel,post_num,address1,address2,created_date) "
					+ " VALUES(member_seq.NEXTVAL,?,?,?,?,?,?,?,?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getNickName());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getTel());
			pstmt.setString(6, member.getPostNum());
			pstmt.setString(7, member.getAddress1());
			pstmt.setString(8, member.getAddress1());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;

		} finally {
			DBUtil.closeResource(pstmt);
		}
		return member;

	}


	
	//
	@Override
	public Member findById(Long id) throws SQLException {
		Member foundMember = null;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM member WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				foundMember = new Member();
				foundMember.setMemberId(rs.getLong("memberId"));
				foundMember.setEmail(rs.getString("email"));
				foundMember.setPassword(rs.getString("password"));
				foundMember.setNickName(rs.getString("nickname"));
				// foundMember.setRoll(rs.getLong("roll"));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return foundMember;

	}

	@Override
	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT id, name, nickname, password, email, tel, post_num, address1, address2, created_date ");
			sb.append("FROM member");

			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Member dto = new Member();

				dto.setMemberId(rs.getLong("id"));
				dto.setName(rs.getString("name"));
				dto.setNickName(rs.getString("nickname"));
				dto.setPassword(rs.getString("password"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
				dto.setCreatedDate(rs.getString("created_date"));

				members.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return members;

	}

	// 회원정보 수정
	@Override
	public Member updateMember(Member member) throws SQLException {

		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE member SET nickname=?, password =?, email =?, tel=?, post_num=?, address1=?, address2=?"
					+ " WHERE id=?";

			pstmt = conn.prepareStatement(sql);
    
			pstmt.setString(1, member.getNickName());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getEmail());	
			pstmt.setString(4, member.getTel());
			pstmt.setString(5, member.getPostNum());
			pstmt.setString(6, member.getAddress1());
			pstmt.setString(7, member.getAddress2());
		    pstmt.setLong(8, member.getMemberId());
			

			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closeResource(pstmt);
		}
		return member;

	}

	// 회원탈퇴
	@Override
	public int deleteMember(long memberId) throws SQLException {

		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE member SET enabled = 0 WHERE  = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.closeResource(pstmt);
		}
		return 0;

	}

	@Override
	public Member findByEmail(String email) {
		/*
		 * PreparedStatement pstmt = null; ResultSet rs = null; Long memberId = null;
		 */

		return null;
	}

	// 회원 / 관리자 구분

	@Override
	public Member Roll(Member member) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {

			// sql = "SELECT roll FROM member WHERE roll = 1 "

		} finally {
			DBUtil.closeResource(pstmt);
		}
		return member;

	}

	// 라멘 
	
	@Override
	public Member readMember(Long memberId) throws SQLException {
		
		Member dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT id, name , nickname, password , email , tel, post_num, address1, address2");
			sb.append(" FROM member" );
			sb.append(" WHERE id =?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setLong(1, memberId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto = new Member();
				dto.setMemberId(rs.getLong("id"));
				dto.setName(rs.getString("name"));
				dto.setNickName(rs.getString("nickName"));
				dto.setPassword(rs.getString("password"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				dto.setPostNum(rs.getString("post_num"));
				dto.setAddress1(rs.getString("address1"));
				dto.setAddress2(rs.getString("address2"));
			
				}
			
				} catch (SQLException e) {
					e.printStackTrace();
		
				} finally {
					DBUtil.closeResource(pstmt, rs);
				}
		return dto;
	}

	
}