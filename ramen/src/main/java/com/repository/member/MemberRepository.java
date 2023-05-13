package com.repository.member;

import com.DTO.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberRepository {
	Member join(Member member) throws SQLException;

	Member findById(Long id) throws SQLException;

	List<Member> findAll();

	Member findByEmail(String email);

	/**
 	 * 나중에
	 */
	int deleteMember(long userId);

	boolean IsUsernameExist(String username);
}