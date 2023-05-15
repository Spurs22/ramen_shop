package com.service.mypage;

import java.util.List;

import com.DTO.RecipeBoard;

public interface MyPageService {

	/**
	 * @param memberId  멤버 아이디
	 * @return          좋아요 누른 포스트 리스트
	 */
	List<RecipeBoard> findLikePost(Long memberId);
	
	
	
}
