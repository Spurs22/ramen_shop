package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.Member;
import com.DTO.RecipeBoard;

public interface RecipeLikeService {

	List<Member> findLikeMembers(Long postId);

	/**
	 * @param memberId  멤버 아이디
	 * @return          좋아요 누른 포스트 리스트
	 */
	List<RecipeBoard> findLikePost(Long memberId);

	/**
	 * @param memberId  멤버 아이디
	 * @param postId    레시피 게시글 아이디
	 * @return          좋아용 여부 Boolean으로 반환
	 */
	Boolean isLike(Long memberId, Long postId);
	
	/**
	 * 					
	 * @param postId	레시피 게시글 아이디
	 * @return			레시피 게시글에 대한 좋아요 수 반환
	 */
	int countLike(Long postId);

	boolean likeRecipePost(Long memberId, Long postId) throws SQLException;
	
	List<RecipeBoard> findLikePost(Long memberId, int offset, int size);
	
	int dataCount(Long memberId);
}
