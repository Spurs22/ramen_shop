package com.servlet.notice;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

import com.DTO.Notice;
import com.repository.notice.NoticeRepository;
import com.repository.notice.NoticeRepositoryImpl;
import com.service.notice.NoticeService;
import com.service.notice.NoticeServiceImpl;
import com.util.MyServlet;
import com.util.MyUtil;

public class NoticeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	NoticeRepository noticeRepository = new NoticeRepositoryImpl();
	
	NoticeService noticeService = new NoticeServiceImpl(noticeRepository);

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		/*
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info == null && uri.indexOf("list.do") == -1) {
			resp.sendRedirect(cp+ "/member/login.do");
			return;
		}
		*/
		
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
		NoticeRepositoryImpl noticerep = new NoticeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String _category = req.getParameter("category");
			int category = 1;
			if(_category != null) {
				category = Integer.parseInt(_category);
			}
			
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
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);
			
			int dataCount, total_page;
			
			if(keyword.length() != 0) {
				dataCount = noticerep.dataCount(category, condition, keyword);
			} else {
				dataCount = noticerep.dataCount(category);
			}
			total_page = util.pageCount(dataCount, size);
			
			
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<Notice> list;
			
			if(keyword.length() != 0) {
				list = noticerep.listNotice(category, offset, size, condition, keyword);
			} else {
				list = noticerep.listNotice(category, offset, size);
			}
			
			// 공지글
			List<Notice> listNotice = null;
			listNotice = noticerep.listNotice(category);
			for(Notice notice : listNotice) {
				notice.setCreate_date(notice.getCreate_date().substring(0, 10));
			}
			
			long gap;
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for(Notice notice : list) {
				Date date = sdf.parse(notice.getCreate_date());
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60);
				notice.setGap(gap);
				
				notice.setCreate_date(notice.getCreate_date().substring(0, 10));
			}
			
			String query = "";
			String listUrl = cp + "/notice/list.do?category=" +category+ "&size=" +size;
			String articleUrl = cp + "/notice/article.do?category=" +category+ "&page=" +current_page+ "&size=" +size;
			
			
			if(keyword.length() != 0 ) {
				query = "condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "utf-8");
			
				listUrl += "&" +query;
				articleUrl += "&" +query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("category", category);
			req.setAttribute("listNotice", listNotice);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("pagee", current_page);
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
		// 
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		// String cp = req.getContextPath();
		
		String category = req.getParameter("category");
		String size = req.getParameter("size");
		
		/*
		if(! info.getMemberId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do?category=" +category);
			return;
		}
		*/
		
		req.setAttribute("category", category);
		req.setAttribute("mode", "write");
		req.setAttribute("size", size);
		
		forward(req, resp, "/WEB-INF/views/notice/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		NoticeRepositoryImpl noticerep = new NoticeRepositoryImpl();
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		String category = req.getParameter("category");
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/notice/list.do?category=" +category);
			return;
		}
		
		/*
		if(! info.getMemberId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do?category=" +category+ "&size=" +size);
			return;
		}
		*/
		
		String size = req.getParameter("size");
		
		try {
			Notice dto = new Notice();
			
			dto.setCategory(Integer.parseInt(category));
			// dto.setmemberId(info.getMemberId());
			
			if(req.getParameter("notice") != null) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			noticerep.insertNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/notice/list.do?category=" +category+ "&size=" +size);
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		String cp = req.getContextPath();
		NoticeRepositoryImpl noticeRep = new NoticeRepositoryImpl();
		// MyUtil util = new MyUtil();
		
		String category = req.getParameter("category");
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "category=" +category+ "&page=" +page+ "&size=" +size;
	
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
			noticeRep.updateHit_count(id);
			
			// 게시물 가져오기
			Notice dto = noticeRep.readNotice(id);
			if(dto == null) {
				resp.sendRedirect(cp+ "/notice/list.do?" +query);
				return;
			}
			// dto.setContent(util.htmlSymbols(dto.getContent()));
			
			dto.setContent(dto.getContent().replaceAll("/n", "<br>"));
			
			// 이전글, 다음글
			int _category = Integer.parseInt(category);
			Notice preReadDto = noticeRep.preReadNotice(_category, dto.getId(), condition, keyword);
			Notice nextReadDto = noticeRep.nextReadNotice(_category, dto.getId(), condition, keyword);
			
			req.setAttribute("category", category);
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
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
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
				
		String cp = req.getContextPath();
		
		/*
		if(! info.getMemberId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do?category=" +category);
			return;
		}
		*/
		
		NoticeRepositoryImpl noticeRep = new NoticeRepositoryImpl();
		
		String category = req.getParameter("category");
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			
			Notice dto = noticeRep.readNotice(id);
			
			if(dto == null) {
				resp.sendRedirect(cp+ "/notice/list.do?category=" +category+ "&page=" +page+ "&size=" +size );
				return;
			}
			
			req.setAttribute("category", category);
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/notice/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?category=" +category+ "&page=" +page+ "&size=" +size);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정완료
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		String category = req.getParameter("category");
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/notice/list.do?category=" +category);
			return;
		}
		
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		
		NoticeRepositoryImpl noticeRep = new NoticeRepositoryImpl();
		
		try {
			Notice dto = new Notice();
			
			dto.setId(Long.parseLong(req.getParameter("id")));
			if (req.getParameter("notice") != null ) {
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			}
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));

			noticeRep.updateNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?category=" +category+ "&page=" +page+ "&size=" +size);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeRepositoryImpl noticeRep = new NoticeRepositoryImpl();
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
						
		String cp = req.getContextPath();
		
		/*
		if(! info.getMemberId().equals("admin")) {
			resp.sendRedirect(cp + "/notice/list.do?category=" +category);
			return;
		}
		*/
		
		String category = req.getParameter("category");
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "category=" +category+ "&page=" +page+ "&size=" +size;
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if (keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "utf-8");
			}
			
			Notice dto = noticeRep.readNotice(id);
			
			if (dto == null) {
				resp.sendRedirect(cp+ "/notice/list.do?" +query);
				return;
			}
			
			/*
			// admin만 삭제 가능
			if(! info.getMemberId().equals("admin")) {
				resp.sendRedirect(cp + "/notice/list.do?" +query);
				return;
			}
			*/
			
			// noticeRep.deleteNotice(id, info.getMemberId());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?" +query);
	}
	
	protected void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		/*
		if(! info.getMemberId().equals("admin")) {
			resp.sendRedirect(cp+ "/notice/list.do?category=" +category);
			return;
		}
		*/
		
		String category = req.getParameter("category");
		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "category=" +category+ "&size=" +size+ "&page=" +page;
		
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		
		try {
			if( keyword != null && keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "UTF-8");
			}
			
			String[] nn = req.getParameterValues("ids");
			Long ids[] = null;
			ids = new Long[nn.length];
			for (int i = 0; i < nn.length; i++) {
				ids[i] = Long.parseLong(nn[i]);
			}
			
			NoticeRepositoryImpl noticeRep = new NoticeRepositoryImpl();
			
			noticeRep.deleteNoticeList(ids);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/notice/list.do?" +query);
		
	}

}
