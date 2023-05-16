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

public class MemberRepositoryImpl implements MemberRepository{

	
	
	private Connection conn = DBConn.getConnection();

	
	
	@Override
	public Member insertMember(Member member) throws SQLException{
		
        // 회원가입
		PreparedStatement pstmt = null;
		String sql;
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO member(id,name,nickname,user_id,password,email,tel,post_num,address1,address1,create_date) "
					+ " VALUES(member_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
	        pstmt.setString(1, member.getName());
	        pstmt.setString(2, member.getNickName());
		    pstmt.setLong(3, member.getMemberId());	
			pstmt.setString(5, member.getPassword());
			pstmt.setString(6, member.getTel());
			pstmt.setString(7, member.getPostNum());
			pstmt.setString(8, member.getAddress1());
			pstmt.setString(9, member.getAddress2());
			pstmt.executeUpdate();
			
			pstmt.close();
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt);
		}
		return member;
		
	}
	
	// 로그인 
	@Override
	public Member loginMember(Member member) throws SQLException {
		
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		
		try {
			
			 sql ="SELECT * FROM member WHERE email=? AND password=? AND enabled=1";
			 pstmt.setString(1, member.getEmail());
		     pstmt.setString(2, member.getPassword());
		     rs = pstmt.executeQuery();
		     
		     if (rs.next()) {
		            // 로그인 성공 처리
		            Member loginMember = new Member();
		            loginMember.setEmail(rs.getString("email"));
		            
		            
		            loginMember.setMemberId(rs.getLong("id"));
		            loginMember.setName(rs.getString("name"));
		            loginMember.setNickName(rs.getString("nickname"));
		            loginMember.setTel(rs.getString("tel"));
		            loginMember.setPostNum(rs.getString("post_num"));
		            loginMember.setAddress1(rs.getString("address1"));
		            loginMember.setAddress2(rs.getString("address2"));
		            return loginMember;
		     } else {
		            // 로그인 실패 처리
		            return null;
		        }
		    } catch (SQLException e) {
				e.printStackTrace();
				throw e;
				
			} finally {
				DBUtil.closeResource(pstmt);
			}
			
		}
	
	
	
	
	
	//
	@Override
	public Member findById(Long id) throws SQLException {
		


		return null;
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
			
	        while(rs.next()) {
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
	    }  catch (SQLException e) {
			e.printStackTrace();
		
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
	return members;
		
	}
	
	

	// 회원정보 수정
	@Override
	public Member updateMember(Member member) throws SQLException{
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE member SET name=?, nickname=?, password =?, email =?, tel=?, post_num=?, address1=?, address2=?"
					+ " WHERE id=?";
			
			pstmt = conn.prepareStatement(sql);
			
		    pstmt.setString(1, member.getName());
		    pstmt.setString(2, member.getNickName());
			pstmt.setLong(3, member.getMemberId());			
			pstmt.setString(5, member.getPassword());
			pstmt.setString(6, member.getTel());
			pstmt.setString(7, member.getPostNum());
			pstmt.setString(8, member.getAddress1());
			pstmt.setString(9, member.getAddress2());	
			pstmt.setLong(9, member.getMemberId()); 
		
			pstmt.executeUpdate();
		
			
		}    catch (SQLException e) {
			e.printStackTrace();
	
		} finally {
			DBUtil.closeResource(pstmt);
		}
			return member;
		
	}

	// 회원탈퇴
	@Override
	public int deleteMember(long userId) throws SQLException{
		
	    PreparedStatement pstmt = null;
	    String sql;
	    
	    try {
	        sql = "UPDATE member SET enabled = 0 WHERE user_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, userId);
	        pstmt.executeUpdate();
	        
	        pstmt.close();

	    }  catch (SQLException e) {
			e.printStackTrace();
	
		} finally {
			DBUtil.closeResource(pstmt);
		}
		return 0;
	
		
	}

	@Override
	public Member findByEmail(String email) {
		/*
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 Long memberId = null;
		*/
		
		return null;
	}


	@Override
	public Member readMember(Member member) throws SQLException {

		PreparedStatement pstmt = null;
	    String sql;
	    ResultSet rs = null;
	    try {
        	sql ="SELECT * FROM member WHERE email = ? AND password = ? AND enabled = 1";

        	 pstmt.setString(1, member.getEmail());
             pstmt.setString(2, member.getPassword());
             rs = pstmt.executeQuery();
        	
             if(rs.next()) {
             Member foundMember = new Member();
             foundMember.setMemberId(rs.getLong("memberId"));
             foundMember.setEmail(rs.getString("email"));
             foundMember.setPassword(rs.getString("password"));
             foundMember.setNickName(rs.getString("nickname"));
            //  foundMember.setRoll(rs.getString("roll"));
                    return foundMember;
             }
             
		} catch (SQLException e) {
			e.printStackTrace();
		
	
		} finally {
			DBUtil.closeResource(pstmt);
		}
		return member;
	
		
	}

	// 회원 / 관리자 구분 
	
	@Override
	public Member Roll(Member member) throws SQLException {
		PreparedStatement pstmt = null;
	    String sql;
	    ResultSet rs = null;
	    
	   try {
		   
		 //  sql = "SELECT roll FROM member WHERE roll = 1 "
		   
		   
		   
		
	} finally {
		DBUtil.closeResource(pstmt);
	}
	return member;

	}
}