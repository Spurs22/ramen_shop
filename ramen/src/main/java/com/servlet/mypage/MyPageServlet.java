package com.servlet.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Product;
import com.DTO.ProductBoard;
import com.DTO.RecipeBoard;
import com.DTO.SessionInfo;
import com.repository.recipe.RecipeBoardRepositoryImpl;
// import com.repository.recipe.RecipeLikeRepositoryImpl;
import com.repository.product.ProductLikeRepositoryImpl;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/mypage/*")
public class MyPageServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if(uri.indexOf("productLikeList.do") != -1) {
			// 내가 찜한 상품 리스트
			productLikeList(req, resp);
		} else if (uri.indexOf("productArticle.do") != -1) {
			// 상품 상세페이지
			productArticle(req, resp);
		} else if (uri.indexOf("recipeLikeList.do") != -1) {
			// 내가 좋아요 한 조합레시피 리스트
			recipeLikeList(req, resp);
		} else if (uri.indexOf("recipeBoardMyList.do") != -1) {
			// 내가 쓴 레시피 리스트
			recipeBoardMyList(req, resp);
		} else if (uri.indexOf("recipeArticle.do") != -1) {
			// 레시피 상세페이지
			recipeArticle(req, resp);
		} else if (uri.indexOf("orderMyList.do") != -1) {
			// 내 주문 리스트
			orderMyList(req, resp);
		} else if (uri.indexOf("orderCancel.do") != -1) {
			// 주문 취소
			orderCancel(req, resp);
		}
		
	}
	
	protected void productLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 찜한 상품리스트
		
		ProductLikeRepositoryImpl dao = new ProductLikeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		// 로그인이 안되어있을 때
		if(info == null) {
			resp.sendRedirect(cp+ "/member/login.do");
			return;
		}
			
			
		try {
			Product dto = new Product();
			String page = req.getParameter("pageNo");
			int current_page = 1;
			if(page != null) 
				current_page = Integer.parseInt(page);
			
			
			int dataCount = dao.getCntLikePost(info.getMemberId());
			
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page -1) * size;
			if(offset < 0) offset = 0;
			
			
			List<ProductBoard> list;
			if(dao.isLike(info.getMemberId(), dto.getProductId()).equals(1)) {
				list = dao.findLikePostById(info.getMemberId(), offset, size);
			} else {
				list = null;
			}
			
			String listUrl = cp+ "/mypage/productLikeList.do";
			String articleUrl = cp + "/mypage/product.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/mypage/productLikeList.jsp");
		
	}
	
	protected void productArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 상세 페이지
		forward(req, resp, "/WEB-INF/views/mypage/productArticle.jsp");
	}
	
	protected void recipeLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 좋아요 한 조합레시피 리스트
		/*
		RecipeLikeRepositoryImpl dao = new RecipeLikeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info == null) {
			resp.sendRedirect(cp+ "/member/login.do");
			return;
		}
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount();
			
			int size = 5;
			
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			
			int offset = (current_page -1 ) * size;
			if(offset < 0) offset = 0;
			
			List<RecipeBoard> list = dao.findLikeMyPost(memberId, offset, size);
			
			String listUrl =  cp+ "/mypage/recipeLikeList.do";
			String articleUrl = cp+ "/mypage/recipeArticle.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/mypage/recipeLikeList.jsp");
		*/
	}
	
	protected void recipeArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 상세 페이지
		forward(req, resp, "/WEB-INF/views/mypage/recipeArticle.jsp");
	}
	
	protected void recipeBoardMyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 작성한 조합레시피 글 목록
		RecipeBoardRepositoryImpl dao = new RecipeBoardRepositoryImpl();
		MyUtil util = new MyUtil();
				
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(info == null) {
			resp.sendRedirect(cp+ "/member/login.do");
			return;
		}
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount();
			
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page -1 ) * size;
			if(offset < 0) offset = 0;
			
			List<RecipeBoard> list = dao.findByMemberId(info.getMemberId(), offset, size);
			
			String listUrl = cp+ "/mypage/recipeBoardMyList.do";
			String articleUrl = cp+ "/mypage/recipeArticle.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/mypage/recipeBoardMyList.jsp");
	}	
	
	protected void orderMyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내 주문 리스트
	}
	
	protected void orderCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 취소
		
	}
}
