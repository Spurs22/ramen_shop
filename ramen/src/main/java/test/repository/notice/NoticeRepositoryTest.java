package test.repository.notice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Notice;
import com.repository.notice.NoticeRepository;
import com.repository.notice.NoticeRepositoryImpl;

public class NoticeRepositoryTest {

	public static void main(String[] args) throws SQLException {
		NoticeRepository noticeRepository = new NoticeRepositoryImpl();
		
		Notice notice = new Notice();
		/*
		notice.setId((long) 1);
		notice.setmemberId((long)1);
		notice.setSubject("공지사항");
		notice.setContent("공지사항임");
		notice.setIp_address("127.0.0.1");
		notice.setCategory(1);
		notice.setNotice(1);
		*/
		/*
		notice.setId((long) 2);
		notice.setmemberId(1);
		notice.setSubject("FAQ");
		notice.setContent("FAQ임");
		notice.setIp_address("127.0.0.1");
		notice.setCategory(2);
		notice.setNotice(1);
		*/
		List<Notice> list = new ArrayList<>();
		
		list.add(notice);
		
		noticeRepository.insertNotice(notice);
	}

}
