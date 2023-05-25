package com.servlet.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.QnA;
import com.DTO.SessionInfo;
import com.repository.qna.QnaRepositoryImpl;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if(uri.indexOf("list.do") != -1 ) {
			list(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1 ) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("reply.do") != -1) {
			replyForm(req, resp);
		} else if(uri.indexOf("reply_ok.do") != -1) {
			replySubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1 ) {
			delete(req, resp);
		}
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}
			
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page -1) * size;
			if(offset <0) offset = 0;
			
			List<QnA> list = null;
			if(keyword.length()==0) {
				list = dao.listQna(offset, size);
			} else {
				list = dao.listQna(offset, size, condition, keyword);
			}
			
			String query = "";
			if(keyword.length() != 0) {
				query = "condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "utf-8");
			}
			
			String listUrl = cp+ "/qna/list.do";
			String articleUrl = cp+ "/qna/article.do?page=" +current_page;
			if(query.length() != 0) {
				listUrl += "?" +query;
				articleUrl += "&" +query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/qna/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/qna/list.do");
			return;
		}
		
		try {
			QnA dto = new QnA();
			
			dto.setMemberId(info.getMemberId());
			
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dao.insertQna(dto, "write");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		String query = "page=" +page;
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "UTF-8");
			}
			
			dao.updateHitCount(id);
			
			QnA dto = dao.readQna(id);
			if(dto == null) {
				resp.sendRedirect(cp+ "/qna/list.do?" +query);
				return;
			}
			dto.setContent(util.htmlSymbols(dto.getContent()));
			
			QnA preReadDto = dao.preReadQna(dto.getGroupNum(), dto.getOrderNo(), condition, keyword);
			QnA nextReadDto = dao.nextReadQna(dto.getGroupNum(), dto.getOrderNo(), condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do?" +query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			QnA dto = dao.readQna(id);
			
			if(dto == null) {
				resp.sendRedirect(cp+ "/qna/list.do?page=" +page);
				return;
			}
			
			if( dto.getMemberId() != info.getMemberId()) {
				resp.sendRedirect(cp+ "/qna/list.do?page=" +page);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do?page=" +page);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/qna/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			QnA dto = new QnA();
			
			dto.setId(Long.parseLong(req.getParameter("id")));
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dto.setMemberId(info.getMemberId());
			
			dao.updateQna(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do?page=" +page);
	}
	
	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			
			QnA dto = dao.readQna(id);
			if(dto == null) {
				resp.sendRedirect(cp+ "/qna/list.do=?page=" +page);
				return;
			}
			
			String s = "[" +dto.getSubject() + "] 에 대한 답변입니다.\n";
			dto.setContent(s);
			
			req.setAttribute("mode", "reply");
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do?page=" +page);
	}
	
	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/qna/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			QnA dto = new QnA();
			
			dto.setSubject(req.getParameter("subject"));
			dto.setContent(req.getParameter("content"));
			
			dto.setGroupNum(Long.parseLong(req.getParameter("groupNum")));
			dto.setOrderNo(Integer.parseInt(req.getParameter("orderNo")));
			dto.setDepth(Integer.parseInt(req.getParameter("depth")));
			dto.setParent(Long.parseLong(req.getParameter("parent")));
			
			dto.setMemberId(info.getMemberId());
			
			dao.insertQna(dto, "reply");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do?page=" +page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaRepositoryImpl dao = new QnaRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" +page;
		
		try {
			long id = Long.parseLong(req.getParameter("id"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length() != 0) {
				query += "&condition=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "UTF-8");
			}
			
			QnA dto = dao.readQna(id);
			
			if(dto == null) {
				resp.sendRedirect(cp+ "/qna/list.do?" +query);
				return;
			}
			
			if( dto.getMemberId() != info.getMemberId() ) {
				resp.sendRedirect(cp+ "/qna/list.do?" +query);
				return;
			}
			
			dao.deleteQna(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/qna/list.do?"+query);
	}	

}
