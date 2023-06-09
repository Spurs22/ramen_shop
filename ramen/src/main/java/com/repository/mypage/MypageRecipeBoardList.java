package com.repository.mypage;

import java.sql.SQLException;
import java.util.List;

import com.DTO.RecipeBoard;

public interface MypageRecipeBoardList {
	// 사용자가 작성한 글 목록 - 제목 조회수 날짜 글 아이디
	List<RecipeBoard> findByMemberId(Long memberId, int offset, int size) throws SQLException;
	
	// 데이터 개수
	int dataCount(Long memberId) throws SQLException;
	
	// 좋아요 한 데이터 개수
	int likedataCount(Long memberId) throws SQLException;
	
	// 사용자가 좋아요 한 게시글 리스트
	List<RecipeBoard> findLikePost(Long memberId, int offset, int size) throws SQLException;	
	
}
