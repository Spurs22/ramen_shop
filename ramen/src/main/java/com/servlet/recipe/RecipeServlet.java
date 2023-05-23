package com.servlet.recipe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DTO.ProductBoard;
import com.DTO.RecipeBoard;
import com.DTO.RecipeComment;
import com.DTO.RecipeProduct;
import com.DTO.SessionInfo;
import com.repository.product.ProductBoardRepository;
import com.repository.product.ProductBoardRepositoryImpl;
import com.repository.recipe.RecipeBoardRepository;
import com.repository.recipe.RecipeBoardRepositoryImpl;
import com.repository.recipe.RecipeCommentRepository;
import com.repository.recipe.RecipeCommentRepositoryImpl;
import com.repository.recipe.RecipeLikeRepository;
import com.repository.recipe.RecipeLikeRepositoryImpl;
import com.service.product.ProductBoardService;
import com.service.product.ProductBoardServiceImpl;
import com.service.recipe.RecipeBoardService;
import com.service.recipe.RecipeBoardServiceImpl;
import com.service.recipe.RecipeCommentService;
import com.service.recipe.RecipeCommentServiceImpl;
import com.service.recipe.RecipeLikeService;
import com.service.recipe.RecipeLikeServiceImpl;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/recipe/*")
public class RecipeServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	RecipeBoardRepository recipeBoardRepository = new RecipeBoardRepositoryImpl();
	RecipeCommentRepository recipeCommentRepository = new RecipeCommentRepositoryImpl();
	RecipeLikeRepository recipeLikeRepository = new RecipeLikeRepositoryImpl();
	
	RecipeBoardService recipeBoardService = new RecipeBoardServiceImpl(recipeBoardRepository);
	RecipeCommentService recipeCommentService = new RecipeCommentServiceImpl(recipeCommentRepository);
	RecipeLikeService recipeLikeService = new RecipeLikeServiceImpl(recipeLikeRepository);
	
	ProductBoardRepository productBoardRepository = new ProductBoardRepositoryImpl();
	ProductBoardService productBoardService = new ProductBoardServiceImpl(productBoardRepository);
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();

		// 파일을 저장할 경로
		

		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.contains("search.do")) {
			search(req, resp);
		} else if (uri.contains("write.do")) {
			writeRecipe(req, resp);
		} else if (uri.contains("write_ok.do")) {
			writeRecipeSubmit(req, resp);
		} else if (uri.contains("update.do")) {
			updateRecipe(req, resp);
		} else if (uri.contains("update_ok.do")) {
			updateRecipeSubmit(req, resp);
		} else if (uri.contains("delete.do")) {
			deleteRecipe(req, resp);
		} else if (uri.contains("recipe.do")) {
			recipe(req, resp);
		} else if (uri.contains("add-comment.do")) {
			writeRecipeComment(req, resp);
		} else if (uri.contains("add-comment_ok.do")) {
			writeRecipeCommentSubmit(req, resp);
		} else if (uri.contains("fix-comment.do")) {
			updateRecipeComment(req, resp);
		} else if (uri.contains("fix-comment_ok.do")) {
			updateRecipeCommentSubmit(req, resp);
		} else if (uri.indexOf("drop-comment.do") != -1) {
			deleteRecipeComment(req, resp);
		} else if (uri.contains("like")) {
			likeRecipe(req, resp);
		} else if (uri.contains("count-comment.do")) {
			countComment(req, resp);
		} else if (uri.contains("contain-post")) {
			productList(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		String cp = req.getContextPath();
		
//		try {
//			// 검색 
//			String condition = req.getParameter("condition");
//			String keyword = req.getParameter("keyword");
//			if(condition == null) {
//				condition = "all";
//				keyword = "";
//			}
//			
//			// GET 방식인 경우 디코딩
//			if(req.getMethod().equalsIgnoreCase("GET")) {
//				keyword = URLDecoder.decode(keyword, "utf-8");
//			}
//			
//			
//			String query = "";
//			if(keyword.length() != 0) {
//				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
//			}
//			
//			// 검색 페이지
//			String listUrl = cp + "/recipe/list.do";
//			String recipeUrl = cp + "/recipe/recipe.do";
//			if(query.length() != 0) {
//				listUrl += "?" + query;
//				recipeUrl += "?" + query;
//			}
//			
//
//			// 수정
//			List<RecipeBoard> list = null;
//
//			String btnradio = String.valueOf(req.getParameter("btnradio"));
//			
//			if(btnradio.equals("btnradio2")) {
//				if(keyword.length() == 0) {
//					list = recipeBoardService.readRecipeByHitCount();
//				} else {
//					list = recipeBoardService.readRecipeByHitCount(condition, keyword);
//				}
//			} else if (btnradio.equals("btnradio1")){
//				if(keyword.length() == 0) {
//					list = recipeBoardService.readRecipe();
//				} else {
//					list = recipeBoardService.readRecipe(condition, keyword);
//				}
//			} else {
//				if(keyword.length() == 0) {
//					list = recipeBoardService.readRecipeByLike();
//				} else {
//					list = recipeBoardService.readRecipeByLike(condition, keyword);
//				}
//			}
//			// 여기까지
//			
//			// 포워딩할 JSP로 넘길 속성
//			req.setAttribute("listUrl", listUrl);
//			req.setAttribute("list", list);
//			req.setAttribute("recipeUrl", recipeUrl);
//			req.setAttribute("condition", condition);
//			req.setAttribute("keyword", keyword);
//			req.setAttribute("btnrado", btnradio);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		// JSP로 포워딩
//		forward(req, resp, "/WEB-INF/views/recipe/recipe-list.jsp");
			
			List<RecipeBoard> list = recipeBoardService.readRecipe();
			
			for(RecipeBoard board : list) {
				System.out.println(board.getPicture());
			}
			
			req.setAttribute("list", list);
			
			System.out.println("레시피 리스트");
			
			forward(req, resp, "/WEB-INF/views/recipe/recipe-list.jsp");
	}
	
	protected void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 정렬
		String btnradio = req.getParameter("btnradio");
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		
		
		resp.setContentType("application/json");
		
		if(keyword.equals("")) keyword = null;
		
		System.out.println(btnradio +","+condition+","+keyword);
		
		List<RecipeBoard> list = recipeBoardService.readRecipeByAll(btnradio, condition, keyword);
		
		JSONArray jarr = new JSONArray();
		for(RecipeBoard board : list) {
			JSONObject json = new JSONObject();
			
			json.put("id", board.getId());
			json.put("subject", board.getSubject());
			json.put("hitCount", board.getHitCount());
			json.put("createdDate", board.getCreatedDate());
			json.put("nickname", board.getNickname());
			json.put("recipeLikeCount", board.getRecipeLikeCount());
			
			String picture = (board.getPicture()==null ? "default2.png" : board.getPicture());
			json.put("picture", picture);
			
			System.out.println("사진 확인 : " + picture);
			jarr.put(json);
			
			System.out.println(board.getId()+","+board.getSubject());
		}
		
		resp.setContentType("text/html;charset=utf-8");

		resp.getWriter().write(jarr.toString());
		
		System.out.println(jarr.toString());
		
	}

	protected void writeRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 쓰기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		req.setAttribute("mode", "write");
		
		List<ProductBoard> posts = productBoardService.findAllPosts();
		req.setAttribute("posts", posts);

		forward(req, resp, "/WEB-INF/views/recipe/recipe-write.jsp");
	}

	protected void writeRecipeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 저장
		List<RecipeProduct> list = new ArrayList<>();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/recipe/recipe-list.do");
			return;
		}
		
		String state = "false";
		String productIds = "";
		String quantities = "";
		
		try {
			RecipeBoard dto = new RecipeBoard();
			
			dto.setSubject(req.getParameter("subject"));
			dto.setMemberId(info.getMemberId());
			dto.setContent(req.getParameter("content"));
			dto.setIpAddress("127.0.0.1");
			
			productIds = req.getParameter("productIds");
		    quantities = req.getParameter("quantities");

		    String[] productIdarr = productIds.split(",");
		    String[] quantityarr = quantities.split(",");
		    
		    for(int i = 0; i < productIdarr.length; i++) {
		    	RecipeProduct product = new RecipeProduct();
		    	
		    	product.setProductId(Long.parseLong(productIdarr[i]));
		    	product.setQuantity(Integer.parseInt(quantityarr[i]));
		    	
		    	System.out.println(product.getProductId());
		    	
		    	list.add(product);
		    }
		    
			dto.setRecipeProduct(list);
			
			recipeBoardService.insertRecipe(dto);
			
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		PrintWriter out = resp.getWriter();
		out.print(job);
	}

	protected void updateRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 수정
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			Long recipe_id = Long.parseLong(req.getParameter("id"));
			
			RecipeBoard board = recipeBoardService.readRecipe(recipe_id);
			if(board == null || ( ! board.getNickname().equals(info.getUserNickname()))) {
				resp.sendRedirect(cp + "/recipe/recipe-list.jsp");
				return;
			}
			
			List<ProductBoard> posts = productBoardService.findAllPosts();
			req.setAttribute("posts", posts);
			
			RecipeBoard dto = recipeBoardRepository.readRecipe(recipe_id);
			
			req.setAttribute("dto", dto);
			
			req.setAttribute("mode", "update");
			req.setAttribute("board", board);
			req.setAttribute("recipeId", recipe_id);
			
			forward(req, resp, "/WEB-INF/views/recipe/recipe-write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/recipe/recipe-list.jsp");
	}

	protected void updateRecipeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 수정 완료
		List<RecipeProduct> list = new ArrayList<>();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/recipe/recipe-list.jsp");
			return;
		}
		
		String state = "true";
		
		try {
			RecipeBoard board = new RecipeBoard();
			
			Long recipe_id = Long.parseLong(req.getParameter("recipeId"));
			
			board.setRecipeId(recipe_id);
			
			board.setSubject(req.getParameter("subject"));
			board.setMemberId(info.getMemberId());
			board.setContent(req.getParameter("content"));
			board.setIpAddress("127.0.0.1");
			
			String productIds = req.getParameter("productIds");
		    String quantities = req.getParameter("quantities");

		    String[] productIdarr = productIds.split(",");
		    String[] quantityarr = quantities.split(",");
		    
		    for(int i = 0; i < productIdarr.length; i++) {
		    	RecipeProduct product = new RecipeProduct();
		    	
		    	product.setProductId(Long.parseLong(productIdarr[i]));
		    	product.setQuantity(Integer.parseInt(quantityarr[i]));
		    	
		    	System.out.println(product.getProductId());
		    	
		    	list.add(product);
		    }
			
			board.setRecipeProduct(list);
			
			board.setMemberId(info.getMemberId());
			
			recipeBoardService.updateRecipe(board);
			
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		PrintWriter out = resp.getWriter();
		out.print(job);
	}

	protected void deleteRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 삭제
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String query = "";
		
		try {
			Long recipe_id = Long.parseLong(req.getParameter("id"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length() != 0) {
				query += "?condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			RecipeBoard board = recipeBoardService.readRecipe(recipe_id);
			
			if(board == null) {
				resp.sendRedirect(cp + "/recipe/recipe-list.do" + query);
				return;
			}
			
			if(info.getMemberId() != board.getMemberId() && ! info.getUserNickname().equals("admin")) {
				resp.sendRedirect(cp + "/recipe/recipe-list.do" + query);
				return;
			}

			recipeBoardService.deleteRecipe(info.getMemberId(), recipe_id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/recipe/recipe-list.do" + query); // 수정
	}

	protected void recipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 상세 보기
		String cp = req.getContextPath();
		
		String query = "";
		String productIds ="";
		String quantities ="";
		
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
				query += "?condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			// 조회수 증가
			recipeBoardService.updateHitCount(id);
			
			RecipeBoard dto = recipeBoardService.readRecipe(id);
			if(dto == null) {
				resp.sendRedirect(cp + "/recipe/list.do" + query);
				return;
			}
			
			List<RecipeProduct> list = dto.getRecipeProduct();
			for(RecipeProduct product : list) {
				product.setName(product.getName());
				product.setQuantity(product.getQuantity());
				
				productIds += product.getProductId() + ",";
				quantities += product.getQuantity() + ",";
			}
			
			productIds = productIds.substring(0, productIds.length() - 1);
			quantities = quantities.substring(0, quantities.length() - 1);
			
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			boolean likeStatus = false;
			if(info != null) {
				likeStatus = ! recipeLikeService.isLike(info.getMemberId(), id);
			}
			
			
			// 이전글 다음글
			RecipeBoard preReadDto = recipeBoardService.preReadRecipe(dto.getId(), condition, keyword);
			RecipeBoard nextReadDto = recipeBoardService.nextReadRecipe(dto.getId(), condition, keyword);
			
			/*
			int replyCount = recipeCommentService.countComment(id);
			
			req.setAttribute("replyCount", replyCount);
			*/
			
			req.setAttribute("list", list);
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			req.setAttribute("likeStatus", likeStatus);
			req.setAttribute("productIds", productIds);
			req.setAttribute("quantities", quantities);
			
			forward(req, resp, "/WEB-INF/views/recipe/recipe-info.jsp");
			return;
			
		} catch (NullPointerException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 resp.sendRedirect(cp + "/recipe/list.do" + query);
	}

	protected void writeRecipeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 달기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String state = "false";
		
		try {
			RecipeComment comment = new RecipeComment();
			
			Long id = Long.parseLong(req.getParameter("id"));
			comment.setBoardId(id);
			comment.setNickname(info.getUserNickname());
			comment.setCotent(req.getParameter("content"));
			comment.setMemberId(info.getMemberId());
			
			recipeCommentService.createComment(comment);
			
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	protected void writeRecipeCommentSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 리스트
		
		try {
			Long id = Long.parseLong(req.getParameter("id"));
			
			int replyCount = 0;
			
			replyCount = recipeCommentService.countComment(id);
			
			List<RecipeComment> listReply = recipeCommentService.findCommentsByPostId(id);
			
			for(RecipeComment comment : listReply) {
				comment.setContent(comment.getContent().replaceAll("\n", "<br>"));
			}
			
			req.setAttribute("listReply", listReply);
			req.setAttribute("replyCount", replyCount);
			
			forward(req, resp, "/WEB-INF/views/recipe/listReply.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
	}

	protected void updateRecipeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 수정
		
	}

	protected void updateRecipeCommentSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 수정 완료 - 없어도 될거 같음
	}

	protected void deleteRecipeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String state = "false";
		
		try {
			Long replyNum = Long.parseLong(req.getParameter("replyNum"));
			
			recipeCommentService.deleteComment(info.getMemberId(), replyNum);
			
			state = "true";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}

	protected void likeRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 좋아요
		System.out.println("레시피 좋아요 왔음");
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		Long memberId = info.getMemberId();

		int recipeLikeCount = 0;
		boolean result = false;
		
		try {
			Long recipeId = Long.parseLong(req.getParameter("id"));

			result = recipeLikeService.likeRecipePost(memberId, recipeId);

			// 좋아요 개수
			recipeLikeCount = recipeLikeService.countLike(recipeId);
			
		} catch (SQLException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject job = new JSONObject();
		job.put("state", result);
		job.put("recipeLikeCount", recipeLikeCount);
		
		PrintWriter out = resp.getWriter();
		out.print(job);
	}

	protected void countComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 개수
		
	}
	
	protected void productList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 이 상품이 포함된 레시피
		List<RecipeBoard> list = new ArrayList<>();
		
		String cp = req.getContextPath();
		
		try {
			Long productId = Long.parseLong(req.getParameter("id"));
			
			System.out.println(productId);
			
			list = recipeBoardService.readRecipeByProduct(productId);
			
			if(list == null || list.size() == 0) {
				resp.sendRedirect(cp + "/product/post-board?id=" + productId);
				return;
			}
			
			req.setAttribute("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/recipe/recipe-list.jsp");
		
	}
	
}
