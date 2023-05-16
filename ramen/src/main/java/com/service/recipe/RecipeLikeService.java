package com.service.recipe;

import java.sql.SQLException;
import java.util.List;

import com.DTO.Member;
import com.DTO.RecipeBoard;

public interface RecipeLikeService {
	/**
	 *
	 * @param memberId  멤버 아이디
	 * @param postId    좋아요 누를 포스트 아이디
	 */
	void likePost(Long memberId, Long postId) throws SQLException;

	/**
	 * @param memberId  멤버 아이디
	 * @param postId    좋아요 취소 할 포스트 아이디
	 */
	void cancelLikePost(Long memberId, Long postId) throws SQLException;

	/**
	 * @param postId    레시피 게시글 번호
	 * @return          좋아요 누른 사람 리스트
	 */
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
	int CountLike(Long postId);
}
