package com.servlet.orderDetail;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.repository.orderDetail.OrderDetailRepositoryImpl;
import com.util.MyServlet;
import com.util.MyUtil;

public class orderDetailServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		/*
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		*/
		
		/*
		1. 관리자페이지 메인화면 ----> admin_main
		2. 송장번호 등록하기(주문처리)  -----> deliveryNumber
		  1) 결제완료인 주문리스트만 불러와서 등록하기
		3. 주문내역 확인하기 -----> orderlist
		  1) 전체 주문리스트
		  2) 회원 주문리스트  -- 회원 검색
		  3) 주문상태별 주문리스트 -- 주문상태별 검색
		  4) 주문번호별 내역 -- 주문번호별 검색
		4. 매출 통계  ----> sales
		  1) 기간별 매출 ---> periodsales
		  2) 상품별 매출 ---> productsales
		*/
		
		// uri에 따른 작업 구분
		if(uri.indexOf("admin_main.do") != -1) { 
			// 관리자 메인 화면
			adminMain(req,resp);
		} else if(uri.indexOf("deliverymanagement.do") != -1) {
			// 배송관리
			deliverymanagement(req,resp);
		} else if(uri.indexOf("ordermanagement.do") != -1) {
			// 주문리스트
			ordermanagement(req,resp);
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
		// 주문 처리
		//OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		//String cp = req.getContextPath();
	}
	
	protected void ordermanagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문리스트
		OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
		MyUtil util = new MyUtil();
		
		//HttpSession session = req.getSession();
		//SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		//String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
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
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		// JSP로 포위딩
		forward(req,resp,"/WEB-INF/views/bbs/orderlist.jsp");
		
	}
	
	protected void sales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 메인화면
	}
	
	protected void periodsales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 - 기간별 매출통계 페이지
	}
	
	protected void productSales(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 - 상품별 매출통계 페이지
	}

	
	

}
