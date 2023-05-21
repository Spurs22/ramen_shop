package com.repository.mypage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.DTO.ProductBoard;

public interface MypageProductRepository {
	// 사용자가 찜 한 상품 리스트
	List<ProductBoard> findLikePostLikeById(Long memberId, int offset, int size) throws SQLException;
	
	// 데이터 개수
	int dataCount(Long memberId) throws SQLException;
	
	// 찜 한 데이터 개수
	int likedataCount(Long memberId) throws SQLException;
	
	ProductBoard getProductBoard(ResultSet rs) throws SQLException;
}
