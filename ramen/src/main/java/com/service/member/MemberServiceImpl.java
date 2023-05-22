package com.service.member;

import com.DTO.Member;
import com.repository.member.MemberRepository;

import java.sql.SQLException;
import java.util.List;

public class MemberServiceImpl implements MemberService {

	MemberRepository memberRepository;

	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public Member insertMember(Member member) throws SQLException {
		return memberRepository.insertMember(member);
	}

	@Override
	public Member loginMember(String email, String password) throws SQLException {
		return memberRepository.loginMember(email, password);
	}

	@Override
	public Member readMember(Long memberId) throws SQLException {
		return memberRepository.readMember(memberId);
	}

	@Override
	public int deleteMember(long memberId) throws SQLException {
		return memberRepository.deleteMember(memberId);
	}

	@Override
	public Member updateMember(Member member) throws SQLException {
		return memberRepository.updateMember(member);
	}

	@Override
	public Member roll(Member member) throws SQLException {
		return memberRepository.roll(member);
	}

	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

}
