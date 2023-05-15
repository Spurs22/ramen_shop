package com.servlet.product;

import com.DTO.Product;
import com.DTO.ProductBoard;
import com.repository.product.ProductBoardRepository;
import com.repository.product.ProductBoardRepositoryImpl;
import com.repository.product.ProductRepository;
import com.repository.product.ProductRepositoryImpl;
import com.service.product.ProductBoardService;
import com.service.product.ProductBoardServiceImpl;
import com.service.product.ProductService;
import com.service.product.ProductServiceImpl;
import com.util.MyServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/product/*")
public class ProductServlet extends MyServlet {

	ProductRepository productRepository = new ProductRepositoryImpl();
	ProductBoardRepository productBoardRepository = new ProductBoardRepositoryImpl();

	ProductService productService = new ProductServiceImpl(productRepository);
	ProductBoardService productBoardService = new ProductBoardServiceImpl(productBoardRepository);
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();

		if (uri.contains("post-form")) {
			postForm(req, resp);
		} else if (uri.contains("post")) {
			if (req.getMethod().equals("POST")) {
				createPost(req, resp);
			} else {
				postInfo(req, resp);
			}
		} else if (uri.contains("list")) {
			list(req, resp);
		} else if (uri.contains("delete")) {
			delete(req, resp);
		} else if (uri.contains("comment-form" )) {
			commentForm(req, resp);
		}
	}

	protected void postForm(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("ProductServlet.postForm" );

		try {
			List<Product> products = productService.findNotRegistedProduct();
			req.setAttribute("products", products);

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
			ProductBoard post = productBoardService.findPostsByProductId(productId);

			req.setAttribute("post", post);

			String path = "/WEB-INF/views/product/product-info.jsp";
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("content");
		int price = Integer.parseInt(req.getParameter("price"));
		Long productId = Long.valueOf(req.getParameter("productId"));
//		req.getParameter("picture");

		System.out.println("content");

		ProductBoard productBoard = new ProductBoard(new Product(productId), 1L, null, content, price);
		productBoardService.createProductPost(productBoard);

		resp.sendRedirect(req.getContextPath() + "/product/list");
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ProductServlet.list" );

		List<ProductBoard> posts = productBoardService.findAllPosts();
		req.setAttribute("posts", posts);

		String path = "/WEB-INF/views/product/product-list.jsp";
		forward(req, resp, path);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) {

	}

	protected void commentForm(HttpServletRequest req, HttpServletResponse resp) {

	}


}
