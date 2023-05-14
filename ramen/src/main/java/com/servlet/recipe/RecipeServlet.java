package com.servlet.recipe;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.RecipeBoard;
import com.DTO.RecipeComment;
import com.DTO.RecipeProduct;
import com.DTO.SessionInfo;
import com.repository.recipe.RecipeBoardRepository;
import com.repository.recipe.RecipeBoardRepositoryImpl;
import com.repository.recipe.RecipeCommentRepository;
import com.repository.recipe.RecipeCommentRepositoryImpl;
import com.util.MyUploadServlet;
import com.util.MyUtil;

public class RecipeServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute(""); // 수정

		if (info == null) {
			forward(req, resp, "/WEB-INF/"); // 수정
			return;
		}

		// 파일을 저장할 경로
		

		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("writeRecipe.do") != -1) {
			writeRecipe(req, resp);
		} else if (uri.indexOf("writeRecipe_ok.do") != -1) {
			writeRecipeSubmit(req, resp);
		} else if (uri.indexOf("updateRecipe.do") != -1) {
			updateRecipe(req, resp);
		} else if (uri.indexOf("updateRecipe_ok.do") != -1) {
			updateRecipeSubmit(req, resp);
		} else if (uri.indexOf("deleteRecipe.do") != -1) {
			deleteRecipe(req, resp);
		} else if (uri.indexOf("recipe.do") != -1) {
			recipe(req, resp);
		} else if (uri.indexOf("writeRecipeComment.do") != -1) {
			writeRecipeComment(req, resp);
		} else if (uri.indexOf("writeRecipeComment_ok.do") != -1) {
			writeRecipeCommentSubmit(req, resp);
		} else if (uri.indexOf("updateRecipeComment.do") != -1) {
			updateRecipeComment(req, resp);
		} else if (uri.indexOf("updateRecipeComment_ok.do") != -1) {
			updateRecipeCommentSubmit(req, resp);
		} else if (uri.indexOf("deleteRecipeComment.do") != -1) {
			deleteRecipeComment(req, resp);
		} else if (uri.indexOf("likeComment.do") != -1) {
			likeRecipe(req, resp);
		} else if (uri.indexOf("dislikeComment.do") != -1) {
			dislikeRecipe(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RecipeBoardRepository board = new RecipeBoardRepositoryImpl();
		
		String cp = req.getContextPath();
		
		try {
			// 검색 
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			// GET 방식인 경우 디코딩
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			List<RecipeBoard> list = null;
			if(keyword.length() == 0) {
				list = board.readRecipe();
			} else {
				list = board.readRecipe(condition, keyword);
			}
			
			String query = "";
			if(keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(query, "utf-8");
			}
			
			// 검색 페이지
			String listUrl = cp + ""; // 수정
			String recipeUrl = cp + ""; // 수정
			if(query.length() != 0) {
				listUrl += "?" + query;
				recipeUrl += "?" + query;
			}
			
			// 포워딩할 JSP로 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("recipeUrl", recipeUrl);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/"); // 수정
	}

	protected void writeRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 쓰기
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/"); // 수정
	}

	protected void writeRecipeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 저장
		RecipeBoardRepository board = new RecipeBoardRepositoryImpl();
		List<RecipeProduct> list = new ArrayList<>();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute(""); // 수정
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + ""); // 수정
			return;
		}
		
		try {
			RecipeBoard dto = new RecipeBoard();
			RecipeProduct product = new RecipeProduct();
			
			dto.setSubject(req.getParameter("subject"));
			dto.setMemberId(info.getMemberId());
			dto.setContent(req.getParameter("content"));
			
			// 어케하누 json list 반환 // 수정
			
			dto.setRecipeProduct(list);
			
			board.insertRecipe(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + ""); // 수정 리스트로 가야댐
	}

	protected void updateRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 수정
		RecipeBoardRepository recipeBoard = new RecipeBoardRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute(""); // 수정
		
		String cp = req.getContextPath();
		
		try {
			Long recipe_id = Long.parseLong(req.getParameter("")); // 수정
			
			RecipeBoard board = recipeBoard.readRecipe(recipe_id);
			if(board == null || ( ! board.getNickname().equals(info.getUserNickname()))) {
				resp.sendRedirect(cp + "");
				return;
			}
			
			req.setAttribute("mode", "update");
			req.setAttribute("board", board);
			
			forward(req, resp, ""); // 수정
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + ""); // 수정
	}

	protected void updateRecipeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 수정 완료
		RecipeBoardRepository recipeBoard = new RecipeBoardRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute(""); // 수정
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "");
			return;
		}
		
		try {
			RecipeBoard board = new RecipeBoard();
			
			board.setId(Long.parseLong(req.getParameter(""))); // 수정
			board.setSubject(req.getParameter("")); // 수정
			board.setContent(req.getParameter("")); // 수정
		
			// json list 변환 // 수정
			
			board.setMemberId(info.getMemberId());
			
			recipeBoard.updateRecipe(board);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + ""); // 수정
	}

	protected void deleteRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 삭제
		RecipeBoardRepository recipeBoard = new RecipeBoardRepositoryImpl();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute(""); // 수정
		
		String query = "";
		
		try {
			Long recipe_id = Long.parseLong(req.getParameter("")); // 수정
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			
			keyword = URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length() != 0) {
				query += "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			RecipeBoard board = recipeBoard.readRecipe(recipe_id);
			
			if(board == null) {
				resp.sendRedirect(cp + "" + query); // 수정
				return;
			}
			
			if(! info.getMemberId().equals(board.getMemberId()) && ! info.getUserNickname().equals("admin")) {
				resp.sendRedirect(cp + "" + query);
				return;
			}
			
			recipeBoard.deleteRecipe(recipe_id, info.getMemberId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "" + query); // 수정
	}

	protected void recipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 상세 보기
		RecipeBoardRepository board = new RecipeBoardRepositoryImpl();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String query = "";
		
		try {
			Long id = Long.parseLong(req.getParameter("postId"));
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
			board.updateHitCount(id);
			
			RecipeBoard dto = board.readRecipe(id);
			if(dto == null) {
				resp.sendRedirect(cp + "" + query);
				return;
			}
			
			dto.setContent(util.htmlSymbols(dto.getContent()));
			
			// 이전글 다음글
			RecipeBoard preReadDto = board.preReadRecipe(dto.getId(), condition, keyword);
			RecipeBoard nextReadDto = board.nextReadRecipe(dto.getId(), condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			forward(req, resp, "/WEB-INF/"); // 수정
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 resp.sendRedirect(cp + "" + query); // 수정
	}

	protected void writeRecipeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 달기
		RecipeCommentRepository recipeComment = new RecipeCommentRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute(""); // 수정
		
		String query = "";
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + ""); // 수정
			return;
		}
		
		try {
			Long id = Long.parseLong(req.getParameter("postId"));
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
			
			RecipeComment comment = new RecipeComment();
			
			comment.setMemberId(info.getMemberId());
			comment.setBoardId(Long.parseLong(req.getParameter("")));
			comment.setCotent(req.getParameter(""));
			
			recipeComment.createComment(comment);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "" + query); // 수정
	}

	protected void writeRecipeCommentSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 달기 완료 - 없어도 될거 같음
	}

	protected void updateRecipeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 수정
	}

	protected void updateRecipeCommentSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 수정 완료 - 없어도 될거 같음
	}

	protected void deleteRecipeComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 댓글 삭제
	}

	protected void likeRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 좋아요
	}

	protected void dislikeRecipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 레시피 좋아요 취소
	}
}
