package com.repository.member;

import com.DTO.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberRepository {
	
	
	
	    // 회원가입
		Member insertMember(Member member) throws SQLException;

		// 아이디로 찾기 
		Member findById(Long id) throws SQLException;
		
		// 회원 탈퇴
		int deleteMember(long userId) throws SQLException;
		
		// 회원정보 수정	
		Member updateMember(Member member) throws SQLException;
		
		// read 
		List<Member> findAll();

		// 
		Member findByEmail(String email);


		// 회원 존재여부
		boolean isUsernameExist(String username);
		
}