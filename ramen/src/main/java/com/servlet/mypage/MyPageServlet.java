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
import com.DTO.OrderItem;
import com.DTO.ProductBoard;
import com.DTO.RecipeBoard;
import com.DTO.RecipeProduct;
import com.DTO.SessionInfo;
import com.repository.recipe.RecipeBoardRepositoryImpl;
import com.repository.recipe.RecipeCommentRepositoryImpl;
import com.repository.mypage.MypageOrderRepositoryImpl;
import com.repository.mypage.MypageRecipeBoardListImpl;
import com.repository.order.OrderRepositoryImpl;
import com.repository.product.ProductLikeRepository;
import com.repository.product.ProductLikeRepositoryImpl;
import com.repository.recipe.RecipeLikeRepositoryImpl;
import com.service.product.ProductLikeService;
import com.service.product.ProductLikeServiceImpl;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/mypage/*")
public class MyPageServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	ProductLikeRepository productLikeRepository = new ProductLikeRepositoryImpl();
	
	ProductLikeService productLikeService = new ProductLikeServiceImpl(productLikeRepository);

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
			
		} else if (uri.indexOf("recipeLikeList.do") != -1) {
			// 내가 좋아요 한 조합레시피 리스트
			recipeLikeList(req, resp);
			
		} else if (uri.indexOf("recipe.do") != -1) {
			// 내가 좋아요 한 레시피 상세페이지
			recipeLikeArticle(req, resp);
			
		} else if (uri.indexOf("recipeBoardMyList.do") != -1) {
			// 내가 쓴 레시피 리스트
			recipeBoardMyList(req, resp);
			
		} else if (uri.indexOf("recipe.do") != -1) {
			// 내가 쓴 레시피 상세페이지
			recipeMyArticle(req, resp);
			
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
		forward(req, resp, "/WEB-INF/views/my_page/main.jsp");
	}
	
	protected void productLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 찜한 상품리스트
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
			
			
		try {
			int dataCount = productLikeService.getCntLikePost(info.getMemberId());
			
			List<ProductBoard> list = productLikeService.findLikePostById(info.getMemberId());
			
			String articleUrl = cp + "/mypage/productArticle.do";
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/my_page/productLikeList.jsp");
		
	}
	
	
	protected void recipeLikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 좋아요 한 조합레시피 리스트
		MypageRecipeBoardListImpl mypagerecipeboardlist = new MypageRecipeBoardListImpl();
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
			
			int dataCount = mypagerecipeboardlist.dataCount(info.getMemberId());
			int likedataCount = mypagerecipeboardlist.likedataCount(info.getMemberId());
			
			int size = 10;
			
			int total_page = util.pageCount(likedataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			
			int offset = (current_page -1 ) * size;
			if(offset < 0) offset = 0;
			
			List<RecipeBoard> list = mypagerecipeboardlist.findLikePost(info.getMemberId(), offset, size);
			
			String listUrl =  cp+ "/mypage/recipeLikeList.do";
			String articleUrl = cp+ "/mypage/recipeArticle.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("likedataCount", likedataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/my_page/recipeLikeList.jsp");
		
	}
	
	protected void recipeLikeArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 좋아요 한 레시피 상세 페이지
		RecipeBoardRepositoryImpl recipeboardrepository = new RecipeBoardRepositoryImpl();
		RecipeLikeRepositoryImpl recipelikerepository = new RecipeLikeRepositoryImpl();
		RecipeCommentRepositoryImpl recipecommentrepository = new RecipeCommentRepositoryImpl();
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
			recipeboardrepository.updateHitCount(id);
			
			RecipeBoard dto = recipeboardrepository.readRecipe(id);
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
				likeStatus = ! recipelikerepository.isLike(info.getMemberId(), id);
			}
			
			// 이전글 다음글
			RecipeBoard preReadDto = recipeboardrepository.preReadRecipe(dto.getId(), condition, keyword);
			RecipeBoard nextReadDto = recipeboardrepository.nextReadRecipe(dto.getId(), condition, keyword);
			
			int replyCount = recipecommentrepository.countComment(id);
			
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
		MypageRecipeBoardListImpl mypagerecipeboardlist = new MypageRecipeBoardListImpl();
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
			int dataCount = mypagerecipeboardlist.dataCount(info.getMemberId());
			int likedataCount = mypagerecipeboardlist.likedataCount(info.getMemberId());
			
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page -1 ) * size;
			if(offset < 0) offset = 0;
			
			List<RecipeBoard> list = mypagerecipeboardlist.findByMemberId(info.getMemberId(), offset, size);
			
			String listUrl = cp+ "/mypage/recipeBoardMyList.do";
			String articleUrl = cp+ "/mypage/recipeArticle.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("likedataCount", likedataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/my_page/recipeBoardMyList.jsp");
	}
	
	protected void recipeMyArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 좋아요 한 레시피 상세 페이지
		RecipeBoardRepositoryImpl recipeboardrepository = new RecipeBoardRepositoryImpl();
		RecipeLikeRepositoryImpl recipelikerepositoty = new RecipeLikeRepositoryImpl();
		RecipeCommentRepositoryImpl recipecommentrepository = new RecipeCommentRepositoryImpl();
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
			recipeboardrepository.updateHitCount(id);
			
			RecipeBoard dto = recipeboardrepository.readRecipe(id);
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
				likeStatus = ! recipelikerepositoty.isLike(info.getMemberId(), id);
			}
			
			// 이전글 다음글
			RecipeBoard preReadDto = recipeboardrepository.preReadRecipe(dto.getId(), condition, keyword);
			RecipeBoard nextReadDto = recipeboardrepository.nextReadRecipe(dto.getId(), condition, keyword);
			
			int replyCount = recipecommentrepository.countComment(id);
			
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
		
		resp.sendRedirect(cp+ "/mypage/recipeBoardMyList.do");
	}
	
	protected void orderMyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내 주문 리스트
		MypageOrderRepositoryImpl mypageorderrepository = new MypageOrderRepositoryImpl();
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
			
			int dataCount = mypageorderrepository.dataCount(info.getMemberId());
			
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1 ) * size;
			if (offset < 0) offset = 0;
			
			List<OrderBundle> list = mypageorderrepository.findOrderAll(info.getMemberId(), offset, size);
			
			String listUrl = cp + "/mypage/orderMyList.do";
			String articleUrl = cp+ "/mypage/articleorderlist.do?page=" +current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("paging", paging);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/my_page/orderMyList.jsp");
	}
	
	protected void articleorderlist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 상세 글보기
		MypageOrderRepositoryImpl mypageorderrepository = new MypageOrderRepositoryImpl();
		
		String cp = req.getContextPath();
		
		try {	
			// 같은 주문번호
			Long orderbundleId = Long.parseLong(req.getParameter("orderBundleId"));
			
			// System.out.println(orderbundleId);
			
			List<OrderItem> orderItem = mypageorderrepository.findOrderDetail(orderbundleId);
			
			String articleUrl = cp+ "/mypage/articleorderlist.do";
			
			req.setAttribute("list", orderItem);
			req.setAttribute("articleUrl", articleUrl);
			
			forward(req, resp, "/WEB-INF/views/my_page/articleorderlist.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+ "/mypage/orderMyList.jsp");
	}
	
	protected void orderCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 주문 취소
		OrderRepositoryImpl orderrepository = new OrderRepositoryImpl();
		
		String cp = req.getContextPath();
		
		try {
			Long orderId = Long.parseLong(req.getParameter("orderId"));
			
			orderrepository.cancelOrder(orderId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+ "/mypage/orderMyList.do");
		
	}
}
