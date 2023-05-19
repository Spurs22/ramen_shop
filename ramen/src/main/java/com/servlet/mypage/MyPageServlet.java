package com.servlet.mypage;

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
import com.DTO.ProductBoard;
import com.DTO.RecipeBoard;
import com.DTO.RecipeProduct;
import com.DTO.SessionInfo;
import com.repository.recipe.RecipeBoardRepositoryImpl;
import com.repository.recipe.RecipeCommentRepositoryImpl;
import com.repository.mypage.MypageOrderRepositoryImpl;
import com.repository.recipe.RecipeLikeRepositoryImpl;
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
		
		
		if(uri.indexOf("main.do") != -1 ) {
			main(req, resp);
			
		} else if(uri.indexOf("productLikeList.do") != -1) {
			// 내가 찜한 상품 리스트
			productLikeList(req, resp);
			
		} else if (uri.indexOf("productArticle.do") != -1) {
			// 상품 상세페이지
			productArticle(req, resp);
			
		} else if (uri.indexOf("recipeLikeList.do") != -1) {
			// 내가 좋아요 한 조합레시피 리스트
			recipeLikeList(req, resp);
			
		} else if (uri.indexOf("recipe.do") != -1) {
			// 내가 좋아요 한 레시피 상세페이지
			recipeArticle(req, resp);
			
		} else if (uri.indexOf("recipeBoardMyList.do") != -1) {
			// 내가 쓴 레시피 리스트
			recipeBoardMyList(req, resp);
			
		} else if (uri.indexOf("my_recipe_article.do") != -1) {
			// 내가 쓴 레시피 상세페이지
			recipeArticle(req, resp);
			
		} else if (uri.indexOf("orderMyList.do") != -1) {
			// 내 주문 리스트
			orderMyList(req, resp);
			
		} else if (uri.indexOf("articleorderlist") != -1) {
			// 주문 상세 페이지
			articleorderlist(req, resp);
			
		} else if (uri.indexOf("orderCancel.do") != -1) {
			// 주문 취소
			orderCancel(req, resp);
		}
		
	}
	
	protected void main(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/mypage/main.jsp");
	}
	
	
	protected void productLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 찜한 상품리스트
		ProductLikeRepositoryImpl dao = new ProductLikeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
			
			
		try {
			// Product dto = new Product();
			String page = req.getParameter("page");
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
			
			
			List<ProductBoard> list = dao.findLikePostById(info.getMemberId(), offset, size);
			
			String listUrl = cp+ "/mypage/productLikeList.do";
			String articleUrl = cp + "/mypage/productArticle.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
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
		forward(req, resp, "/WEB-INF/views/recipe/recipe-info.jsp");
	}
	
	protected void recipeLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 좋아요 한 조합레시피 리스트
		RecipeLikeRepositoryImpl dao = new RecipeLikeRepositoryImpl();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount(info.getMemberId());
			
			int size = 5;
			
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			
			int offset = (current_page -1 ) * size;
			if(offset < 0) offset = 0;
			
			List<RecipeBoard> list = dao.findLikePost(info.getMemberId(), offset, size);
			
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
			throw e;
		}
		
		forward(req, resp, "/WEB-INF/views/mypage/recipeLikeList.jsp");
		
	}
	
	protected void recipeArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 좋아요 한 레시피 상세 페이지
		RecipeBoardRepositoryImpl boarddao = new RecipeBoardRepositoryImpl();
		RecipeLikeRepositoryImpl likedao = new RecipeLikeRepositoryImpl();
		RecipeCommentRepositoryImpl commentdao = new RecipeCommentRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String query = "";
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		try {
			Long id = Long.valueOf(req.getParameter("id"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length() != 0) {
				query += "?condtion=" +condition+ "&keyword=" +URLEncoder.encode(keyword, "utf-8");
			}
			
			// 조회수 up
			boarddao.updateHitCount(id);
			
			RecipeBoard dto = boarddao.readRecipe(id);
			if(dto == null) {
				resp.sendRedirect(cp+ "/mypage/recipeLikeList.do");
				return;
			}
			
			List<RecipeProduct> list = dto.getRecipeProduct();
			for(RecipeProduct product : list) {
				product.setName(product.getName());
				product.setQuantity(product.getQuantity());
			}
			
			dto.setContent(util.htmlSymbols(dto.getContent()));
			
			boolean likeStatus = false;
			if(info != null) {
				likeStatus = ! likedao.isLike(info.getMemberId(), id);
			}
			
			// 이전글 다음글
			RecipeBoard preReadDto = boarddao.preReadRecipe(dto.getId(), condition, keyword);
			RecipeBoard nextReadDto = boarddao.nextReadRecipe(dto.getId(), condition, keyword);
			
			int replyCount = commentdao.countComment(id);
			
			String articleUrl = cp+ "/recipe/recipe-info.jsp";
			
			req.setAttribute("replyCount", replyCount);
			
			req.setAttribute("list", list);
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("articleUrl", articleUrl);
			
			req.setAttribute("likeStatus", likeStatus);
			
			forward(req, resp, "/WEB-INF/views/recipe/recipe-info.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+ "/mypage/recipeLikeList.do");
	}
	
	protected void recipeBoardMyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 작성한 조합레시피 글 목록
		RecipeBoardRepositoryImpl dao = new RecipeBoardRepositoryImpl();
		MyUtil util = new MyUtil();
				
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// dataCount에 memberId이여야 dataCount가
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
			req.setAttribute("page", current_page);
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
		MypageOrderRepositoryImpl dao = new MypageOrderRepositoryImpl();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount(info.getMemberId());
			
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1 ) * size;
			if (offset < 0) offset = 0;
			
			List<OrderBundle> list = dao.findOrderAll(info.getMemberId(), offset, size);
			
			String listUrl = cp + "/mypage/orderMyList.do";
			String articleUrl = cp+ "/mypage/articleorderlist.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("paging", paging);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/mypage/orderMyList.jsp");
	}
	
	protected void articleorderlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 상세 글보기
	}
	
	protected void orderCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 취소
		
	}
}
