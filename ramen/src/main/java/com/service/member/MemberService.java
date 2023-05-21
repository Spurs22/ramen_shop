package com.service.member;

import com.DTO.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {


	// 회원가입
	Member insertMember(Member member) throws SQLException;

	// 아이디로 찾기
	Member findById(Long memberId) throws SQLException;

	// 로그인
	public Member loginMember(String email, String password) throws SQLException;

	// read Member
	public Member readMember(Long memberId) throws SQLException;

	// 회원 탈퇴
	int deleteMember(long memberId) throws SQLException;

	// 회원정보 수정
	public Member updateMember(Member member) throws SQLException;

	// 회원 , 관리자 구분
	Member Roll(Member member)throws SQLException;

	List<Member> findAll();


	// 이메일로 찾기
	Member findByEmail(String email);
	
	
}
