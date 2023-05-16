package com.service.notice;

import java.sql.SQLException;
import java.util.List;

import com.DTO.Notice;
import com.repository.notice.NoticeRepository;

public class NoticeServiceImpl implements NoticeService {

	NoticeRepository noticeRepository;
	
	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	
	@Override
	public void insertNotice(Notice dto) throws SQLException {
		noticeRepository.insertNotice(dto);
	}

	@Override
	public int dataCount(int category) {
		return noticeRepository.dataCount(category);
	}

	@Override
	public int dataCount(int category, String condition, String keyword) {
		return noticeRepository.dataCount(category, condition, keyword);
	}

	@Override
	public List<Notice> listNotice(int category, int offset, int size) {
		return noticeRepository.listNotice(category, offset, size);
	}

	@Override
	public List<Notice> listNotice(int category, int offset, int size, String condition, String keyword) {
		return noticeRepository.listNotice(category, offset, size, condition, keyword);
	}

	@Override
	public List<Notice> listNotice(int category) {
		return noticeRepository.listNotice(category);
	}

	@Override
	public Notice readNotice(Long id) {
		return noticeRepository.readNotice(id);
	}

	@Override
	public Notice preReadNotice(int category, Long id, String condition, String keyword) {
		return noticeRepository.preReadNotice(category, id, condition, keyword);
	}

	@Override
	public Notice nextReadNotice(int category, Long id, String condition, String keyword) {
		return noticeRepository.nextReadNotice(category, id, condition, keyword);
	}

	@Override
	public void updateHit_count(Long id) throws SQLException {
		noticeRepository.updateHit_count(id);
	}

	@Override
	public void updateNotice(Notice dto) throws SQLException {
		noticeRepository.updateNotice(dto);
	}

	@Override
	public void deleteNotice(Long id) throws SQLException {
		noticeRepository.deleteNotice(id);
	}

	@Override
	public void deleteNoticeList(Long[] ids) throws SQLException {
		noticeRepository.deleteNoticeList(ids);
	}

}
