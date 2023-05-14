package com.servlet.product;

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
			post(req, resp);
		} else if (uri.contains("list")) {
			list(req, resp);
		} else if (uri.contains("delete")) {
			delete(req, resp);
		} else if (uri.contains("comment-form" )) {
			commentForm(req, resp);
		}
	}

	protected void postForm(HttpServletRequest req, HttpServletResponse resp) {

	}

	protected void post(HttpServletRequest req, HttpServletResponse resp) {

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
