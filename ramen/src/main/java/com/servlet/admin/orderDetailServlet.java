package com.servlet.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.OrderBundle;
import com.DTO.OrderStatistics;
import com.DTO.SessionInfo;
import com.repository.admin.OrderDetailRepositoryImpl;
import com.repository.order.OrderRepositoryImpl;
import com.util.MyServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/admin/*")
public class orderDetailServlet extends MyServlet{
	private static final long serialVersionUID = 1L;
	private OrderRepositoryImpl ori = new OrderRepositoryImpl();
	private OrderDetailRepositoryImpl odri = new OrderDetailRepositoryImpl();
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		// 로그인이 안되어있으면 로그인 화면으로 포워드
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		/*
		1. 관리자페이지 메인화면 ----> admin_main
		  1) 매출 현황(그래프) --- 추후에 할 것
		2. 송장번호 등록하기(주문처리)  ----> deliverymanagement
		  1) 결제완료인 주문리스트만 불러와서 등록하기
		3. 주문내역 확인하기 ----> ordermanagement
		  1-1) 전체 주문리스트 ---> ordermanagement
		  1-1) 전체 >> 주문 상세리스트 ---> ordermanagementDetail 
		  2-1) 회원 주문리스트(회원 검색)
		  2-2) 회원 >> 주문 상세리스트
		  3-1) 주문상태별 주문리스트(주문상태 검색)
		  3-2) 주문상태 >> 주문 상세리스트
		  4-1) 주문번호별 주문리스트(주문번호 검색)
		  4-2) 주문번호 >> 주문 상세리스트
		4. 매출 통계  ----> sales_statistics
		  1) 기간별 + 상품별 매출
		*/
		
		// uri에 따른 작업 구분
		if(uri.indexOf("deliverymanagement.do") != -1) { 
			// 배송관리
			deliverymanagement(req,resp);
		} else if(uri.indexOf("ordermanagement.do") != -1) {
			// 주문관리(전체)
			ordermanagement(req,resp);
		} else if(uri.indexOf("ordermanagement_detail.do") != -1) {
			// 주문관리(상세)
			ordermanagementDetail(req,resp);
		} else if(uri.indexOf("sales_statistics.do") != -1) {
			// 매출통계 메인화면
			salesStatistics(req,resp);
		} else if(uri.indexOf("delivery_ok.do") != -1) {
			deliveryok(req,resp);
		} else if(uri.indexOf("delivery_completeok.do") != -1) {
			delivery_completeok(req,resp);
		}
	}
	
	protected void deliverymanagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 처리(송장번호 등록하면 배송중으로 바뀜)
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String cp = req.getContextPath();
		
		try {
			// 넘어온 페이지
			String page = req.getParameter("page"); 
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 한페이지 표시할 데이터 개수
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);

			int dataCount, total_page;
			
			int status = 0;
			String ststatusId = req.getParameter("statusId");
			if(ststatusId != null) {
				status = Integer.parseInt(ststatusId);
			}
		
			dataCount = odri.dataCount(status);
			
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
					
			String query = "";
			
			// 페이징처리
			String listUrl = cp + "/admin/deliverymanagement.do";
			
			String listDetailUrl = cp + "/admin/deliverymanagement.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				listDetailUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
	
			List<OrderBundle> orderBundlelist;
			
			orderBundlelist = odri.findOrderAll(offset, size, status);
			
			// ordermanagement.jsp에 넘겨줄 데이터		
			req.setAttribute("orderBundlelist", orderBundlelist);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", listDetailUrl);
			req.setAttribute("paging", paging);

		} catch (Exception e) {
			e.printStackTrace();
		}
		 forward(req,resp,"/WEB-INF/views/admin/deliverymanagement.jsp");
		
	}
	protected void deliveryok(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 송장번호 등록 완료
	
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		String cp = req.getContextPath();
		try {
			
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			Long deliveryId = Long.parseLong(req.getParameter("deliveryId"));
			
			odri.setDeliveryNumber(orderId, deliveryId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/admin/deliverymanagement.do");
	}
	
	protected void delivery_completeok(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 배송완료 등록 완료
	
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		String cp = req.getContextPath();
		try {
			
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			
			odri.deliveryComplete(orderId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/admin/deliverymanagement.do");
	}
	
	protected void ordermanagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문리스트
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String cp = req.getContextPath();
		
		try {
			// 넘어온 페이지
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
			
			// 주문 상태
			int status = 0;
			String statusId = req.getParameter("status");
			if(statusId != null) {
				status = Integer.parseInt(statusId);
			}
			
			// 한페이지 표시할 데이터 개수
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 5 : Integer.parseInt(pageSize);

			int dataCount, total_page;
			if (keyword.length() != 0) {
				dataCount = odri.dataCount(condition, keyword, status);
			} else {
				dataCount = odri.dataCount(status);
			}
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}
			
			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			int orderBundleId = 0;
			String orderId = req.getParameter("orderBundleId");
			if(orderId != null) {
				orderBundleId = Integer.parseInt(orderId);
			}
			
			List<OrderBundle> orderBundlelist;
			if (keyword.length() != 0) {
				orderBundlelist = odri.findOrderAll(offset, size, condition, keyword, status);
			} else {
				orderBundlelist = odri.findOrderAll(offset, size, status);
			}
			
			// 페이징처리
			String query = "status=" + status;
			String orderIdURL = "orderBundleId=" + orderId;
			String listUrl = cp + "/admin/ordermanagement.do?size=" + size;
			String articleUrl = cp + "/admin/ordermanagement_detail.do?page=" + current_page + "&size=" + size;
			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
				listUrl += "&" + query;
				articleUrl += "&" + query;
				orderId += "&" + query + "&" + orderIdURL;
			}
			orderId += "&" + query + "&" + orderIdURL; 
			
			String paging = util.paging(current_page, total_page, listUrl);

			// ordermanagement.jsp에 넘겨줄 데이터		
			req.setAttribute("orderBundlelist", orderBundlelist);
			req.setAttribute("status", status);
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
		// JSP로 포위딩
		forward(req,resp,"/WEB-INF/views/admin/ordermanagement.jsp");
		
	}
	protected void ordermanagementDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상세페이지
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		try {
			// 넘어온 페이지
			String page = req.getParameter("page"); 

			Long orderId = Long.parseLong(req.getParameter("orderId"));
			
			OrderBundle orderBundlelist = odri.findOrderDetail(orderId);
				
			// ordermanagement.jsp에 넘겨줄 데이터		
			req.setAttribute("orderBundlelist", orderBundlelist);
			req.setAttribute("page", page);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포위딩
		forward(req,resp,"/WEB-INF/views/admin/ordermanagement_detail.jsp");
	}
	
	protected void salesStatistics(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매출통계 메인화면
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		String cp = req.getContextPath();
		try {
			/*
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int dataCount = odri.dataCount();
			
			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			
			// 전체페이지보다 표시할 페이지가 큰 경우
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 데이터(게시물) 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<OrderStatistics> OrderStatistics = null;
			odri.SalesStatisticsByProduct(offset, size);
			
			// 페이징 처리
			String listUrl = cp + "/admin/list.do";
			String articleUrl = cp + "/admin/article.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			*/
			
			int proid = 0;
			String mode = req.getParameter("proid");
			if(mode!=null) {
				proid = Integer.parseInt(mode);
			}
			
			List<OrderStatistics> os = odri.salesStatisticsByProduct(proid);
		
			req.setAttribute("os",os);
			req.setAttribute("proid",proid);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/admin/sales_statistics.jsp");

	}
}
