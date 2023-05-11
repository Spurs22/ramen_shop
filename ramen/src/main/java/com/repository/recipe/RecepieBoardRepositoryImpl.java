package com.repository.recipe;

import java.util.List;

import com.DTO.ProductBoard;

public class RecepieBoardRepositoryImpl implements RecepieBoardRepository {

	@Override
	public Long createPost(ProductBoard productBoard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editPost(ProductBoard productBoard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int deletePost(Long memberId, Long postId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ProductBoard> findPostsByMemberId(Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductBoard findPostsByPostId(Long postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBoard> findAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registPicture(Long postId, String path) {
		// TODO Auto-generated method stub
		
	}

}
