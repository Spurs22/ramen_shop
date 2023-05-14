package com.repository.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Member;
import com.util.DBConn;

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
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}
	return null;
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
	        sb.append("SELECT id, name, nickname, user_id, password, email, tel, post_num, address1, address2, created_date ");
	        sb.append("FROM member");
		
	        pstmt = conn.prepareStatement(sb.toString());
	        rs = pstmt.executeQuery();
			
	        while(rs.next()) {
	            Member dto = new Member();
				
	            dto.setMemberId(null);
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
	    } 
	    catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException e) {}
	        }
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {}
	        }
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
		
			
		}  catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
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
	        pstmt = null;
	        
	        sql = "DELETE FROM member WHERE user_id = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, userId);
	        int deletedRows = pstmt.executeUpdate();
	        
	        return deletedRows;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if(pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	            }
	        }
	    }
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

	
	
	
	


}