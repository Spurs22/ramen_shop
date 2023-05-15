package com.servlet.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.SessionInfo;
// import com.repository.product.ProductLikeRepositoryImpl;
import com.util.MyServlet;
// import com.util.MyUtil;

@WebServlet("/MyPage/*")
public class MyPageServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info == null) {
			forward(req, resp, "/WEB-INF/views/member.login.jsp");
			return;
		}
		
		if(uri.indexOf("productLikeList.do") != -1) {
			productLikeList(req, resp);
		} else if (uri.indexOf("productArticle.do") != -1) {
			productArticle(req, resp);
		} else if (uri.indexOf("recipeLikeList.do") != -1) {
			recipeLikeList(req, resp);
		} else if (uri.indexOf("recipeArticle.do") != -1) {
			recipeArticle(req, resp);
		} else if (uri.indexOf("recipeBoardMyList.do") != -1) {
			recipeBoardMyList(req, resp);
		} else if (uri.indexOf("cancelorder.do") != -1) {
			cancelorder(req, resp);
		} else if (uri.indexOf("orderMyList") != -1) {
			orderMyList(req, resp);
		}
		
	}
	
	protected void productLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 찜 목록
		/*
		ProductLikeRepositoryImpl productLikeRepository = new ProductLikeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) 
				current_page = Integer.parseInt(page);
			
			int dataCount;
			dataCount = productLikeRepository.dataCount();
			
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page -1) * size;
			if(offset < 0) offset = 0;
			
			List<Product> list = productLikeRepository.findLikePost(memberId, offset, size);
			
			String listUrl = cp+ "/MyPage/productLikeList.do";
			String articleUrl = cp + "/MyPage/product.do?page=" +current_page;
			
			// String 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		*/
	}
	
	protected void productArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 상세 페이지
		
	}
	
	protected void recipeLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 좋아요 목록
		
	}
	
	protected void recipeArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 상세 페이지
		
	}
	
	protected void recipeBoardMyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 작성한 레시피 조합 글 목록
		
	}	
	
	protected void cancelorder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 취소
		
	}
	
	protected void orderMyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내 주문 리스트
		
	}
}
