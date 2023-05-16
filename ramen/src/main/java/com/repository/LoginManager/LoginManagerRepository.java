package com.repository.LoginManager;

import java.sql.SQLException;

import com.DTO.Member;

public interface LoginManagerRepository {

	
	// 로그인
	 Member login(String userId, String password) throws SQLException;
	
	
	// 로그아웃
	 void logout(Member member) throws SQLException;

}
