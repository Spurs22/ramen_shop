package com.repository.LoginManager;

import java.sql.SQLException;

import com.DTO.Member;
import com.repository.member.MemberRepository;

public class LoginManagerRepositoryImpl implements LoginManagerRepository{

	  public LoginManagerRepositoryImpl(MemberRepository memberRepository) {
	   
	   }
	  
	 
	@Override
	public Member login(String userId, String password) throws SQLException {
		return null;
		/*
		 Member member = memberRepository.findById(userId);
		 
		 if(member != null && member.getPassword().equals(password)) {
			 return member;
		 }
		 
		 else {
			 throw new SQLException("로그인 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
		 }
		*/
	}

	@Override
	public void logout(Member member) throws SQLException {


		
	}
}


