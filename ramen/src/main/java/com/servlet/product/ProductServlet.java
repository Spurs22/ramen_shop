package com.servlet.product;

import com.DTO.*;
import com.repository.cart.CartRepository;
import com.repository.cart.CartRepositoryImpl;
import com.repository.member.MemberRepository;
import com.repository.member.MemberRepositoryImpl;
import com.repository.order.OrderRepository;
import com.repository.order.OrderRepositoryImpl;
import com.repository.product.*;
import com.service.cart.CartService;
import com.service.cart.CartServiceImpl;
import com.service.member.MemberService;
import com.service.member.MemberServiceImpl;
import com.service.order.OrderService;
import com.service.order.OrderServiceImpl;
import com.service.product.*;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.SessionUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

@MultipartConfig
@WebServlet("/product/*")
public class ProductServlet extends MyUploadServlet {

	CartRepository cartRepository = new CartRepositoryImpl();
	ProductRepository productRepository = new ProductRepositoryImpl();
	ProductBoardRepository productBoardRepository = new ProductBoardRepositoryImpl();
	ProductCommentRepository productCommentRepository = new ProductCommentRepositoryImpl();
	ProductLikeRepository productLikeRepository = new ProductLikeRepositoryImpl();
	MemberRepository memberRepository = new MemberRepositoryImpl();
	OrderRepository orderRepository = new OrderRepositoryImpl();

	MemberService memberService = new MemberServiceImpl(memberRepository);
	CartService cartService = new CartServiceImpl(cartRepository);
	ProductService productService = new ProductServiceImpl(productRepository);
	ProductBoardService productBoardService = new ProductBoardServiceImpl(productBoardRepository);
	ProductCommentService productCommentService = new ProductCommentServiceImpl(productCommentRepository);
	ProductLikeService productLikeService = new ProductLikeServiceImpl(productLikeRepository);
	OrderService orderService = new OrderServiceImpl(orderRepository);

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();

		if (uri.contains("post-form")) {
			postForm(req, resp);
		} else if (uri.contains("post-board")) {
			if (req.getMethod().equals("POST")) {
				createPost(req, resp);
			} else {
				postInfo(req, resp);
			}
		} else if (uri.contains("edit-board")) {
			if (req.getMethod().equals("POST")) {
				editPost(req, resp);
			} else {
				editPostForm(req, resp);
			}
		} else if (uri.contains("list")) {
			list(req, resp);
		} else if (uri.contains("delete-board")) {
			deleteBoard(req, resp);
		} else if (uri.contains("delete")) {
			delete(req, resp);
		} else if (uri.contains("comment-form" )) {
			commentForm(req, resp);
		} else if (uri.contains("search")) {
			searchKeyword(req, resp);
		} else if (uri.contains("like")) {
			likeProductBoard(req, resp);
		} else if (uri.contains("add-cart")) {
			addCart(req, resp);
		} else if (uri.contains("review-form")) {
			reviewForm(req, resp);
		} else if (uri.contains("add-form")) {
			productAddForm(req, resp);
		} else if (uri.contains("post-product")) {
			if (req.getMethod().equals("POST")) {
				createProduct(req, resp);
			} else {
				productInfo(req, resp);
			}
		} else if (uri.contains("edit-product")) {
			if (req.getMethod().equals("POST")) {
				editProduct(req, resp);
			} else {
				editProductForm(req, resp);
			}
		} else if (uri.contains("valid-product-name")) {
			validProductName(req, resp);
		} else if (uri.contains("manage-product")) {
			manageProduct(req, resp);
		}
	}

	private void likeProductBoard(HttpServletRequest req, HttpServletResponse resp) {

		System.out.println("ProductServlet.postForm" );
		HttpSession session = req.getSession();
		Boolean result = false;

		try {
			Object sessionMember = session.getAttribute("member");
			if (sessionMember == null) {
				// 로그인 안한상태로 좋아요 클릭시
				resp.sendRedirect(req.getContextPath() + "/product/list");
				return;
			}

			Long memberId = ((SessionInfo) sessionMember).getMemberId();


			Long recipeId = Long.parseLong(req.getParameter("id"));
			System.out.println("멤버 : " + memberId + ", 게시글 아이디" + recipeId);

			result = productLikeService.likeProduct(memberId, recipeId);
			System.out.println(result);

			JSONObject job = new JSONObject();
			job.put("state", result);

			PrintWriter out = resp.getWriter();
			out.print(job);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void postForm(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("ProductServlet.postForm" );

		try {
			List<Product> products = productService.findNotRegisteredProduct();

			Long memberId = SessionUtil.getMemberIdFromSession(req);

			System.out.println(memberId);

			req.setAttribute("products", products);
			req.setAttribute("mode", "post");
//			req.setAttribute("editBoardPost", null);

			String path = "/WEB-INF/views/product/product-form.jsp";
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void postInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ProductServlet.postInfo" );

		Long productId = Long.valueOf(req.getParameter("id" ));
		try {
			HttpSession session = req.getSession();

			Object sessionMember = session.getAttribute("member");
			Long memberId = null;
			Boolean likeStatus = false;
			if (sessionMember != null) {
				memberId = ((SessionInfo) sessionMember).getMemberId();
				likeStatus = productLikeService.isLike(memberId, productId);
			}

			ProductBoard post = productBoardService.findPostByProductId(productId);
			List<ProductComment> comments = productCommentService.findCommentsByProductId(productId);

			req.setAttribute("likeStatus", likeStatus);
			req.setAttribute("post", post);
			req.setAttribute("comments", comments);
			req.setAttribute("memberId", memberId);

			String path = "/WEB-INF/views/product/product-info.jsp";
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long memberId = SessionUtil.getMemberIdFromSession(req);

		if (memberId == null) {
			resp.sendRedirect(req.getContextPath() + "/product/list");
			return;
		}

		String content = req.getParameter("content");
		int price = Integer.parseInt(req.getParameter("price"));
		Long productId = Long.valueOf(req.getParameter("productId"));

		ProductBoard productBoard = new ProductBoard(new Product(productId), memberId, null, content, price);

		ServletContext context = getServletContext();
		String path = context.getRealPath("/resource/picture");

		System.out.println(path);

		List<String> fileNameList = doFileUpload(req.getParts(), path);


		if (fileNameList != null) {
			for (String s : fileNameList) {
				System.out.println(s);
			}
		}

		if (fileNameList != null) {
			productBoard.setImgList(fileNameList);
		}

		System.out.println("content");
		System.out.println("content = " + content + ", price = " + price + ", productId = " + productId);

		productBoardService.createProductPost(productBoard);

		resp.sendRedirect(req.getContextPath() + "/product/list");
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ProductServlet.list" );

		try {
			Long memberId = SessionUtil.getMemberIdFromSession(req);
			if (memberId != null) {
				Member member = memberService.readMember(memberId);
				req.setAttribute("member", member);
			}

			List<ProductBoard> posts = productBoardService.findByCategoryAndKeyword(ProductCategory.BONGJI, null);
			req.setAttribute("posts", posts);

			String path = "/WEB-INF/views/product/product-list.jsp";
			forward(req, resp, path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void searchKeyword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("검색 요청 받음");

		String inputCategory = req.getParameter("category");
		String keyword = req.getParameter("keyword");

		resp.setContentType("application/json");

		System.out.println(inputCategory + " " + keyword);

		ProductCategory productCategory = null;

		if (inputCategory != null) {
			productCategory = ProductCategory.getByValue(Integer.parseInt(inputCategory));
		}

		if (keyword.equals("")) keyword = null;

		List<ProductBoard> posts = productBoardService.findByCategoryAndKeyword(productCategory, keyword);

		JSONArray jsonArray = new JSONArray();
		for (ProductBoard post : posts) {
			JSONObject jsonPost = new JSONObject();
			jsonPost.put("productId", post.getProduct().getProductId());
			jsonPost.put("productName", post.getProduct().getName());
			jsonPost.put("picture", post.getProduct().getPicture());
			jsonPost.put("rating", post.getRating());
			jsonPost.put("price", post.getPrice());
			jsonArray.put(jsonPost);
		}

		resp.setContentType("text/html;charset=utf-8");

		resp.getWriter().write(jsonArray.toString());

		System.out.println(jsonArray);
	}

	protected void editPostForm(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("ProductServlet.editProductBoard");

		Long productId = Long.valueOf(req.getParameter("id"));

		System.out.println(productId);
		ProductBoard editBoard = productBoardService.findPostByProductId(productId);

		try {
			List<Product> products = productService.findNotRegisteredProduct();
			req.setAttribute("products", products);
			req.setAttribute("editBoard", editBoard);
			req.setAttribute("mode", "edit");

			String path = "/WEB-INF/views/product/product-form.jsp";
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void editPost(HttpServletRequest req, HttpServletResponse resp) {
		String content = req.getParameter("content");
		int price = Integer.parseInt(req.getParameter("price"));
		Long productId = Long.valueOf(req.getParameter("productId"));
//		req.getParameter("picture");

		Long memberId = SessionUtil.getMemberIdFromSession(req);

		try {
			System.out.println("content");
			System.out.println("content = " + content + ", price = " + price + ", productId = " + productId);

			ProductBoard productBoard = new ProductBoard(new Product(productId), memberId, null, content, price);

			System.out.println(productBoard);

			productBoardService.editPost(productBoard);

			resp.sendRedirect(req.getContextPath() + "/product/post?id=" + productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void addCart(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("ProductServlet.add-cart" );

		Boolean result = false;
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = null;

		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Long memberId = SessionUtil.getMemberIdFromSession(req);
			if (memberId == null) {
				//
				resp.sendRedirect(req.getContextPath() + "/product/list");
				return;
			}

			int quantity = Integer.parseInt(req.getParameter("quantity"));
			Long productId = Long.parseLong(req.getParameter("id"));

			System.out.println("멤버 : " + memberId + ", 상품 아이디 : " + productId + ", 개수 : " + quantity);

			// 장바구니에 추가
			cartService.createItem(productId, memberId, quantity);

			result = true;
		} catch (Exception e) {
			result = false;
		}

		jsonObject.put("state", result);
		Objects.requireNonNull(out).print(jsonObject);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) {

	}

	protected void commentForm(HttpServletRequest req, HttpServletResponse resp) {

	}

	protected void reviewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("review-form");

		try {
			Long memberId = SessionUtil.getMemberIdFromSession(req);
			if (memberId == null) {
				resp.sendRedirect(req.getContextPath() + "/home");
				return;
			}

			Long productId = Long.valueOf(req.getParameter("product-id"));
			Long orderId = Long.valueOf(req.getParameter("order-id"));

			// 이미 남긴 리뷰가 있는지, 구매했는지, 확인


			//
			ProductBoard post = productBoardService.findPostByProductId(productId);
			if (post == null) {
				resp.sendRedirect(req.getContextPath() + "/home");
				return;
			}



			req.setAttribute("post", post);

			String path = "/WEB-INF/views/product/product-review.jsp";
			forward(req, resp, path);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void productAddForm(HttpServletRequest req, HttpServletResponse resp) {
		String path = "/WEB-INF/views/product/product-add-form.jsp";

		try {
			req.setAttribute("mode", "post");
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void validProductName(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("ProductServlet.validProductName");

		Boolean result = false;

		try {
			String productName = req.getParameter("name").replaceAll(" ", "");
			System.out.println("전달받은 상품 이름 : " + productName);


			// 존재하면 true 반환
			result = productService.isPresentName(productName);
			System.out.println(result);

			JSONObject job = new JSONObject();

			// 존재하면 false 반환
			job.put("state", !result);

			PrintWriter out = resp.getWriter();
			out.print(job);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createProduct(HttpServletRequest req, HttpServletResponse resp) {

		try {
			Long memberId = SessionUtil.getMemberIdFromSession(req);

			if (memberId == null) {
				resp.sendRedirect(req.getContextPath() + "/product/list");
				return;
			}

			ProductCategory category = ProductCategory.getByValue(Integer.parseInt(req.getParameter("category")));
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			String name = req.getParameter("name");


			Product product = new Product(category, name, quantity, null);

			ServletContext context = getServletContext();
			String path = context.getRealPath("/resource/picture");

			Part part = req.getPart("picture");

			String fileName = doFileUpload(part, path);

			System.out.println(fileName);
			product.setPicture(fileName);

			System.out.println(product);

			productService.createProduct(product);

			resp.sendRedirect(req.getContextPath() + "/product/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void productInfo(HttpServletRequest req, HttpServletResponse resp) {

	}
	protected void editProduct(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Long memberId = SessionUtil.getMemberIdFromSession(req);

			if (memberId == null) {
				resp.sendRedirect(req.getContextPath() + "/product/list");
				return;
			}

			ProductCategory category = ProductCategory.getByValue(Integer.parseInt(req.getParameter("category")));
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			String name = req.getParameter("name");
			Long productId = Long.valueOf(req.getParameter("id"));

			Product product = new Product(productId, category, name, quantity, null);

			ServletContext context = getServletContext();
			String path = context.getRealPath("/resource/picture");


			Part part = req.getPart("picture");
			String fileName = doFileUpload(part, path);

			String currentPicture = productService.findProductByProductId(productId).getPicture();

			// 업로드한 파일이 있고, 현재 파일이 있다면 현재 파일 삭제 후 새 파일 업로드.
			if (fileName != null && currentPicture != null) {
				FileManager.doFiledelete(path, currentPicture);
				System.out.println(currentPicture + " 삭제");
				System.out.println(fileName + " 생성");
			} else if (fileName == null && currentPicture != null) { // 업로드한 파일이 없고, 현재 파일이 있다면 현재 파일을 객체에 다시 등록
				fileName = currentPicture;
			}

			product.setPicture(fileName);

			System.out.println(product);

			productService.editProduct(productId, product);

			resp.sendRedirect(req.getContextPath() + "/product/manage-product");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void editProductForm(HttpServletRequest req, HttpServletResponse resp) {
		String path = "/WEB-INF/views/product/product-add-form.jsp";

		try {
			Long productId = Long.valueOf(req.getParameter("id"));
			Product product = productService.findProductByProductId(productId);

			req.setAttribute("product", product);
			req.setAttribute("mode", "edit");
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void manageProduct(HttpServletRequest req, HttpServletResponse resp) {
		String path = "/WEB-INF/views/product/manage-product.jsp";

		try {
			List<Product> products = productService.findAllProduct();
			req.setAttribute("products", products);
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sample(HttpServletRequest req, HttpServletResponse resp) {
		try {
			forward(req, resp, "/WEB-INF/views/mypage/mypage-home.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void deleteBoard(HttpServletRequest req, HttpServletResponse resp) {
		try {

			SessionInfo sessionInfo = SessionUtil.getSessionFromSession(req);

			if (sessionInfo == null) {
				resp.sendRedirect(req.getContextPath() + "/product/list");
				return;
			}

			Long productId = Long.valueOf(req.getParameter("id"));
			Long memberId = sessionInfo.getMemberId();
			Long userRoll = sessionInfo.getUserRoll();
			int i = productBoardService.deletePost(memberId, userRoll, productId);

			if (i == 0) {
				System.out.println("인증 성공");
			}

			resp.sendRedirect(req.getContextPath() + "/product/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
