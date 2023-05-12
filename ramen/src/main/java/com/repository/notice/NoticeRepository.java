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
	
	// 공지글
	List<Notice> listNotice();
	
	Notice readNotice(long id);
	
	// 이전 글
	Notice preReadNotice(long id, String condition, String keyword);
	
	// 다음 글
	Notice nextReadNotice(long id, String condition, String keyword);
	
	void updateHitCount(long id) throws SQLException;
	
	void updateNotice(Notice dto) throws SQLException;
	
	void deleteNotice(long id) throws SQLException;
	
	void deleteNoticeList(long [] ids) throws SQLException;
	
}
