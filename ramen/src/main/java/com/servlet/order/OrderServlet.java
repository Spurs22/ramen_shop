package com.servlet.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DTO.Cart;
import com.DTO.Order;
import com.repository.cart.CartRepositoryImpl;
import com.repository.order.OrderRepositoryImpl;
import com.util.MyServlet;


@WebServlet("/order/*")
public class OrderServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		
		/*  // 세션 정보
		HttpSession session = req.getSession();
		// >> 로그인 구현하고나서 변경될 가능성 있음 ㅇㅇ
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info == null) {
			// forward(req,resp,"/WEB-INF/views/member/login.jsp");
			return;
		}
		*/
		
		// 1) 주문 폼
		// 2) 주문 완료
		if(uri.indexOf("order.do")!= -1) {
			orderForm(req,resp);
		} else if(uri.indexOf("order-ok.do")!= -1) {
			orderSubmit(req,resp);
		}  	
		
	}

	private void orderForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1) 주문 폼
				System.out.println("장바구니 물건 선택 > 주문");
				
				//HttpSession session = req.getSession();
				//SessionInfo info = (SessionInfo) session.getAttribute("member");
				
				String cp = req.getContextPath();
				
				try {
					String[] pi = req.getParameterValues("productIds");
					long[] products = null;
					products = new long[pi.length];
					for(int i=0; i<pi.length; i++) {
						products[i] = Long.parseLong(pi[i]);
					}
					
					CartRepositoryImpl cartRepositoryImpl = new CartRepositoryImpl();
					
					// 아이디 ..
					long memberId = (long)1;
					List<Cart> list = cartRepositoryImpl.transferCartList(memberId, products);
					Long totalPrice = 0L;
					
					for(Cart c : list) {
						totalPrice += c.getPrice()*c.getQuantity();
					}
					
					req.setAttribute("list", list);
					req.setAttribute("totalPrice", totalPrice);
					
				}  catch (Exception e) {
					e.printStackTrace();
				}
				
				
				// order 완료 창으로 forward
				forward(req, resp, "/WEB-INF/views/order/order-list.jsp");
	}

	private void orderSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 2) 주문 완료
		System.out.println("주문완료했다.");
		Order order = new Order();
		
		OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl();
		try {	
			
			// 아이디...
			long memberId = 1L;
			
			// 파라미터
			order.setMemberId(memberId);
			order.setReceiveName(req.getParameter("receiveName"));
			
			String tel = req.getParameter("tel1") + "-" + req.getParameter("tel2") + "-" +  req.getParameter("tel1");
			order.setTel(tel);
			
			order.setPostNum(req.getParameter("zip"));
			order.setAddress1(req.getParameter("zip1"));
			order.setAddress2(req.getParameter("zip2"));
			
			// 주문 bundle 생성
			long order_id = orderRepositoryImpl.createOrderBundle(order);
			
			String[] pi = req.getParameterValues("items");
			long[] products = null;
			products = new long[pi.length];
			for(int i=0; i<pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}
			
			CartRepositoryImpl cartRepositoryImpl = new CartRepositoryImpl();
			List<Cart> list = cartRepositoryImpl.transferCartList(memberId, products);
			
			
			for(Cart c : list) {
				Order order2 = new Order();
				long oneprice = orderRepositoryImpl.orderPrice(c.getProductId());
				long price = oneprice * c.getQuantity();
				
				order2.setOrderId(order_id);
				order2.setProductId(c.getProductId());
				order2.setQuantity(c.getQuantity());
				order2.setPrice(price);
				order2.setFinalPrice(price);
				
				// 주문 item 추가
				orderRepositoryImpl.createOrderList(order2);
			}
			
			long totalPrice = orderRepositoryImpl.orderAllPrice(order_id);
			
			req.setAttribute("totalPrice", totalPrice);
			req.setAttribute("orderId",order_id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/order/order-ok.jsp");
	}


}
