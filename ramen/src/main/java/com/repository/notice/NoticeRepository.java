package com.repository.notice;

import java.sql.SQLException;
import java.util.List;

import com.DTO.Notice;

public interface NoticeRepository {
	void insertNotice(Notice dto) throws SQLException;
	
	int dataCount();
	
	// 검색에서 전체의 개수
	int dataCount(String condition, String keyword);
	
	// 게시물 리스트
	List<Notice> listNotice(int offset, int size);
	
	// 검색에서 리스트
	List<Notice> listNotice(int offset, int size, String condition, String keyword);
	
	// 공지글 - 파라미터 카테고리 -> 10년 전 글이여도 공지글!
	List<Notice> listNotice();
	
	// 해당 공지글 보기
	Notice readNotice(Long id);
	
	// 이전 글
	Notice preReadNotice(Long id, String condition, String keyword);
	
	// 다음 글
	Notice nextReadNotice(Long id, String condition, String keyword);
	
	// 조회수 증가
	void updateHitCount(Long id) throws SQLException;
	
	// 공지글 수정
	void updateNotice(Notice dto) throws SQLException;
	
	// 공지글 삭제
	void deleteNotice(Long id) throws SQLException;
	
	
	void deleteNoticeList(Long [] ids) throws SQLException;
	
}
