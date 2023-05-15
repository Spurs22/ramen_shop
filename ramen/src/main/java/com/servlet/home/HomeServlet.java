package com.servlet.home;

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

@WebServlet("/home/*")
public class HomeServlet extends MyServlet {

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();

		homePage(req, resp);
//		if (uri.contains("post-form")) {
//			postForm(req, resp);
//		} else if (uri.contains("post")) {
//			if (req.getMethod().equals("POST")) {
//				createPost(req, resp);
//			} else {
//				postInfo(req, resp);
//			}
//		} else if (uri.contains("list")) {
//			list(req, resp);
//		} else if (uri.contains("delete")) {
//			delete(req, resp);
//		} else if (uri.contains("comment-form" )) {
//			commentForm(req, resp);
//		}
	}

	protected void homePage(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("HomeServlet.homePage");

		try {

			String path = "/WEB-INF/views/home/home.jsp";
			forward(req, resp, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
