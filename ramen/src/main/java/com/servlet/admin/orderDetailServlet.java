package com.servlet.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;
import com.DTO.OrderStatistics;
import com.DTO.SessionInfo;
import com.repository.admin.OrderDetailRepositoryImpl;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/admin/*")
public class orderDetailServlet extends MyServlet{
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
		
		/*
		1. 관리자페이지 메인화면 ----> admin_main
		2. 송장번호 등록하기(주문처리)  ----> deliverymanagement
		  1) 결제완료인 주문리스트만 불러와서 등록하기
		3. 주문내역 확인하기 ----> ordermanagement
		  1-1) 전체 주문리스트 --> ordermanagement
		  1-1) 전체 >> 주문 상세리스트 --> ordermanagementDetail 
		  2-1) 회원 주문리스트(회원 검색)
		  2-2) 회원 >> 주문 상세리스트
		  3-1) 주문상태별 주문리스트(주문상태 검색)
		  3-2) 주문상태 >> 주문 상세리스트
		  4-1) 주문번호별 주문리스트(주문번호 검색)
		  4-2) 주문번호 >> 주문 상세리스트
		4. 매출 통계  ----> sales
		  1) 기간별 매출 ---> periodsales
		  2) 상품별 매출 ---> productsales
		*/
		
		// uri에 따른 작업 구분
		if(uri.indexOf("main.do") != -1) { 
			// 관리자 메인화면
			adminMain(req,resp);
		} else if(uri.indexOf("deliverymanagement.do") != -1) {
			// 배송관리
			deliverymanagement(req,resp);
		} else if(uri.indexOf("ordermanagement.do") != -1) {
			// 주문관리(전체)
			ordermanagement(req,resp);
		} else if(uri.indexOf("ordermanagement.do") != -1) {
			// 주문관리(상세)
			ordermanagementDetail(req,resp);
		} else if(uri.indexOf("sales.do") != -1) {
			// 매출통계 메인화면
			sales(req,resp);
		} else if(uri.indexOf("periodsales.do") != -1) {
			// 매출통계 - 기간별 매출통계 페이지
			periodsales(req,resp);
		} else if(uri.indexOf("productsales.do") != -1) {
			// 매출통계 - 상품별 매출통계 페이지
			productSales(req,resp);
		}
	}
	
	protected void adminMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 관리자 메인 화면
		//OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		//String cp = req.getContextPath();
	}
	
	protected void deliverymanagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 처리(송장번호 등록하면 배송중으로 바뀜)
		OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			OrderItem odi = new OrderItem();
			
			//odi.setStatusId(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//resp.sendRedirect(cp + "/board/deliverymanagement.do?page=" + page);
		
	}
	
	protected void ordermanagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문리스트
		OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page"); // 넘어온 페이지
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			if(condition == null) { // 전체 주문리스트
				condition = "all";
				keyword = "";
			}
			
			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = odri.dataCount();
			} else {
				dataCount = odri.dataCount(condition, keyword);
			}
			
			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;

			List<OrderBundle> OrderBundlelist = null;
			//List<OrderItem> OrderItemlist = null;

			if (keyword.length() == 0) {
				//OrderBundlelist = odri.findOrderAll(offset, size, condition, keyword, StatusId);
			} else {
				//OrderBundlelist = odri.findOrderDetail(offset, size, condition, keyword);
			}
			
			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			// 페이징처리
			String listUrl = cp + "/admin/ordermanagement.do";
			
			String listDetailUrl = cp + "/admin/ordermanagementDetail.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				listDetailUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			
			// guest.jsp에 넘겨줄 데이터
			req.setAttribute("list", OrderBundlelist);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", listDetailUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포위딩
		forward(req,resp,"/WEB-INF/views/orderdetail/ordermanagement.jsp");
		
	}
	protected void ordermanagementDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상세페이지
		// 주문 상세 리스트에서 번들을 꽉 채워서 보여주기
		
	}
	
	protected void sales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 메인화면
		
	}
	
	protected void periodsales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 - 기간별 매출통계 페이지
		OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		
		//HttpSession session = req.getSession();
		//SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			OrderStatistics os = new OrderStatistics();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/admin/periodsales.do");
	}
	
	protected void productSales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 - 상품별 매출통계 페이지
		OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		
		//HttpSession session = req.getSession();
		//SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			OrderStatistics os = new OrderStatistics();
			
			odri.SalesStatisticsByProduct();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/admin/productsales.do");
	}
}
