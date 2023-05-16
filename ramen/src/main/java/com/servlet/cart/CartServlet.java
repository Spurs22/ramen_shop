package com.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Cart;
import com.DTO.SessionInfo;
import com.repository.cart.CartRepositoryImpl;
import com.util.MyServlet;

@WebServlet("/cart/*")
public class CartServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		// 1) 장바구니를 본다. - 30일 이후 지나면 삭제
		// 2) 장바구니의 물건을 취소한다.
		if (uri.indexOf("list.do") != -1) {
			cartList(req, resp);
		} else if (uri.indexOf("list-delete.do") != -1) {
			cartListDelete(req, resp);
		} 
	}

	protected void cartList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1) 장바구니를 본다. - 30일 이후 지나면 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		CartRepositoryImpl cri = new CartRepositoryImpl();

		try {
			// 장바구니 30일 이후 품목 삭제
			cri.deleteAutoCart();

			List<Cart> list = null;

			Long memberId = info.getMemberId();
			list = cri.findCartByMemberId(memberId);

			// 장바구니 총 개수 구하기
			int dataCount = cri.getCnt(memberId);

			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/cart/cart-list.jsp");
	}

	protected void cartListDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 2) 장바구니의 물건 리스트를 취소한다.
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		try {

			String[] pi = req.getParameterValues("productIds");
			long[] products = null;
			products = new long[pi.length];
			for (int i = 0; i < pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}

			CartRepositoryImpl cri = new CartRepositoryImpl();

			long memberId = info.getMemberId();

			// 상품 삭제
			cri.deleteCartList(memberId, products);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/cart/list.do");
	}

}
