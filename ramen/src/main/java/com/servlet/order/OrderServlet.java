package com.servlet.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Cart;
import com.DTO.OrderBundle;
import com.DTO.OrderItem;
import com.DTO.SessionInfo;
import com.repository.cart.CartRepositoryImpl;
import com.repository.order.OrderRepositoryImpl;
import com.util.MyServlet;

@WebServlet("/order/*")
public class OrderServlet extends MyServlet {
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

		// 1) 주문 폼
		// 2) 주문 완료
		if (uri.indexOf("order.do") != -1) {
			orderForm(req, resp);
		} else if (uri.indexOf("order-ok.do") != -1) {
			orderSubmit(req, resp);
		}

	}

	private void orderForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1) 주문 폼
		System.out.println("장바구니 물건 선택 > 주문");

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		try {
			String[] pi = req.getParameterValues("productIds");
			long[] products = null;
			products = new long[pi.length];
			for (int i = 0; i < pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}

			CartRepositoryImpl cartRepositoryImpl = new CartRepositoryImpl();

			long memberId = info.getMemberId();
			List<Cart> list = cartRepositoryImpl.transferCartList(memberId, products);
			Long totalPrice = 0L;

			for (Cart c : list) {
				totalPrice += c.getPrice() * c.getQuantity();
			}

			req.setAttribute("list", list);
			req.setAttribute("totalPrice", totalPrice);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/order/order-list.jsp");
	}

	private void orderSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 2) 주문 완료
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		OrderBundle orderBundle = new OrderBundle();

		OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl();
		try {

			long memberId = info.getMemberId();

			orderBundle.setMemberId(memberId);
			orderBundle.setReceiveName(req.getParameter("receiveName"));

			String tel = req.getParameter("tel1") + "-" + req.getParameter("tel2") + "-" + req.getParameter("tel1");
			orderBundle.setTel(tel);

			orderBundle.setPostNum(req.getParameter("zip"));
			orderBundle.setAddress1(req.getParameter("zip1"));
			orderBundle.setAddress2(req.getParameter("zip2"));

			// 주문 bundle 생성
			long order_id = orderRepositoryImpl.createOrderBundle(orderBundle);

			String[] pi = req.getParameterValues("items");
			long[] products = null;
			products = new long[pi.length];
			for (int i = 0; i < pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}

			CartRepositoryImpl cartRepositoryImpl = new CartRepositoryImpl();
			List<Cart> list = cartRepositoryImpl.transferCartList(memberId, products);

			for (Cart c : list) {
				OrderItem orderItem = new OrderItem();
				long oneprice = orderRepositoryImpl.orderPrice(c.getProductId());
				long price = oneprice * c.getQuantity();

				orderItem.setOrderBundleId(order_id);
				orderItem.setProductId(c.getProductId());
				orderItem.setQuantity(c.getQuantity());
				orderItem.setPrice(price);
				orderItem.setFinalPrice(price);

				orderRepositoryImpl.createOrderList(orderItem);
			}

			long totalPrice = orderRepositoryImpl.orderAllPrice(order_id);

			req.setAttribute("totalPrice", totalPrice);
			req.setAttribute("orderId", order_id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/order/order-ok.jsp");
	}

}
