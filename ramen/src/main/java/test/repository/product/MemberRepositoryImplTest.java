package test.repository.product;


import java.sql.SQLException;

import com.DTO.Member;
import com.repository.member.MemberRepository;
import com.repository.member.MemberRepositoryImpl;

public class MemberRepositoryImplTest {

	public static void main(String[] args) throws SQLException {
	
		MemberRepository memberRepository = new MemberRepositoryImpl();

		Member member = new Member();
		member.setAddress1(null);
		memberRepository.insertMember(member);
	
	}
	
	
}
