package com.repository.qna;

import java.sql.SQLException;
import java.util.List;

import com.DTO.QnA;

public interface QnaRepository {
	// 데이터 추가
	void insertQna(QnA dto, String mode) throws SQLException;
	
	// 답변일 경우 orderNo 변경
	void updateOrderNo(long groupNum, int orderNo) throws SQLException;
	
	// 데이터 개수
	int dataCount();
	
	// 검색에서의 데이터 개수
	int dataCount(String condition, String keyword);
	
	// 게시물 리스트
	List<QnA> listQna(int offset, int size);
	
	List<QnA> listQna(int offset, int size, String condition, String keyword);
	
	// 조회수 증가
	void updateHitCount(long id) throws SQLException;
	
	// 해당 게시물 보기
	QnA readQna(long id);
	
	// 이전 글
	QnA preReadQna(long groupNum, int orderNo, String condition, String keyword);
	
	// 다음 글
	QnA nextReadQna(long groupNum, int orderNo, String condition, String keyword);
	
	// 게시물 수정
	void updateQna(QnA dto) throws SQLException;
	
	// 게시물 삭제
	void deleteQna(long id) throws SQLException;
	
}
