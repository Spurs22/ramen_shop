package com.servlet.notice;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Notice;
import com.DTO.SessionInfo;
//import com.repository.notice.NoticeRepository;
import com.repository.notice.NoticeRepositoryImpl;
import com.util.MyServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/notice/*")
public class NoticeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(info == null && uri.indexOf("list.do") == -1) {
			resp.sendRedirect(cp+ "/member/login.do");
			return;
		}
		
		
		if(uri.indexOf("list.do") != -1 ) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("deleteList.do") != -1) {
			deleteList(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 리스트
		NoticeRepositoryImpl dao = new NoticeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 5 : Integer.parseInt(pageSize);
			//if(pageSize == null) pageSize = "10";
			
			int dataCount, total_page;
			
			if(keyword.length() != 0) {
				dataCount = dao.dataCount(condition, keyword);
			} else {
				dataCount = dao.dataCount();
			}
			total_page = util.pageCount(dataCount, size);
			
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<Notice> list;
			
			if(keyword.length() != 0) {
				list = dao.listNotice(offset, size, condition, keyword);
			} else {
				list = dao.listNotice(offset, size);
			}
			
			// 공지글
			List<Notice> listNotice = dao.listNotice();
			for(Notice notice : listNotice) {
				notice.setCreatedDate(notice.getCreatedDate().substring(0, 10));
			}
			
			long gap;
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(Notice notice : list) {
				Date date = sdf.parse(notice.getCreatedDate());
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60);
				notice.setGap(gap);
				
				notice.setCreatedDate(notice.getCreatedDate().substring(0, 10));
			}
			
			String query = "";
			String listUrl = cp + "/notice/list.do?size=" +size;
			String articleUrl = cp + "/notice/article.do?page=" +current_page+ "&size=" +size;
			
			if(keyword.length() != 0 ) {
				query = "condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "utf-8");
			
				listUrl += "&" +query;
				articleUrl += "&" +query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("listNotice", listNotice);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 쓰기 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String size = req.getParameter("size");
		//if(size == null) size = "10";
		
		// 관리자만 글 등록 가능
		if(!(info.getUserRoll() == 1)) {
			resp.sendRedirect(cp + "/notice/list.do?&size=" +size);
			return;
		}
		
		req.setAttribute("mode", "write");
		req.setAttribute("size", size);
		
		forward(req, resp, "/WEB-INF/views/notice/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		NoticeRepositoryImpl noticerepository = new NoticeRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		// String size = req.getParameter("size");
		// if(size == null) size = "10";
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/notice/list.do");
			return;
		}
		
		// 관리자만 글 등록
		if(! (info.getUserRoll() == 1)) {
			resp.sendRedirect(cp + "/notice/list.do" );
			return;
		}
		
		try {
			Notice dto = new Notice();
			
			dto.setMemberId(info.getMemberId());
			
			if(req.getParameter("notice") != null) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			noticerepository.insertNotice(dto);
			// noticerepository.insertNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/notice/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		
		String cp = req.getContextPath();
		NoticeRepositoryImpl noticerepository = new NoticeRepositoryImpl();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" +page+ "&size=" +size;
	
		try {
			long id = Long.parseLong(req.getParameter("id"));
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if (keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "UTF-8");
			}
			
			// 조회수 증가
			noticerepository.updateHitCount(id);
			
			// 게시물 가져오기
			Notice dto = noticerepository.readNotice(id);
			if(dto == null) {
				resp.sendRedirect(cp+ "/notice/list.do?" +query);
				return;
			}
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			// 이전글, 다음글
			Notice preReadDto = noticerepository.preReadNotice(dto.getId(), condition, keyword);
			Notice nextReadDto = noticerepository.nextReadNotice(dto.getId(), condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("size", size);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			forward(req, resp, "/WEB-INF/views/notice/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?" +query);
	
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
				
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		if(! (info.getUserRoll() == 1)) {
			resp.sendRedirect(cp + "/notice/list.do");
			return;
		}
		
		NoticeRepositoryImpl noticerepository = new NoticeRepositoryImpl();
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			
			Notice dto = noticerepository.readNotice(id);
			
			if(dto == null) {
				resp.sendRedirect(cp+ "/notice/list.do");
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("size", size);
			
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/notice/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정완료
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/notice/list.do");
			return;
		}
		
		if(! (info.getUserRoll() == 1)) {
			resp.sendRedirect(cp+ "/notice/list.do");
			return;
		}
		
		// String page = req.getParameter("page");
		// String size = req.getParameter("size");
		
		NoticeRepositoryImpl noticerepository = new NoticeRepositoryImpl();
		
		try {
			Notice dto = new Notice();
			
			dto.setId(Long.parseLong(req.getParameter("id")));
			if (req.getParameter("notice") != null ) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));

			noticerepository.updateNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do");
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeRepositoryImpl noticerepository = new NoticeRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
						
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" +page+ "&size=" +size;
		
		// 관리자만 삭제 가능
		if(! (info.getUserRoll() == 1)) {
			resp.sendRedirect(cp + "/notice/list.do?");
			return;
		}
		
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "UTF-8");
			
			if (keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "UTF-8");
			}
			
			Notice dto = noticerepository.readNotice(id);
			
			if (dto == null) {
				resp.sendRedirect(cp+ "/notice/list.do?" +query);
				return;
			}
			
	
			noticerepository.deleteNotice(id);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?" +query);
	}
	
	protected void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "size=" +size+ "&page=" +page;
		
		// 관리자만 삭제 가능
		if(! (info.getUserRoll() == 1)) {
			resp.sendRedirect(cp+ "/notice/list.do");
			return;
		}
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		
		try {
			if( keyword != null && keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "UTF-8");
			}
			
			String[] nn = req.getParameterValues("ids");
			Long ids[] = null;
			ids = new Long[nn.length];
			for(int i = 0; i < nn.length; i++) {
				ids[i] = Long.parseLong(nn[i]);
			}
			
			NoticeRepositoryImpl noticerepository = new NoticeRepositoryImpl();
			
			noticerepository.deleteNoticeList(ids);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?" +query);
		
	}

}
