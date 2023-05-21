package com.servlet.order;

import java.io.IOException;
import java.util.ArrayList;
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
import com.repository.cart.CartRepository;
import com.repository.cart.CartRepositoryImpl;
import com.repository.order.OrderRepository;
import com.repository.order.OrderRepositoryImpl;
import com.repository.product.ProductRepository;
import com.repository.product.ProductRepositoryImpl;
import com.service.cart.CartService;
import com.service.cart.CartServiceImpl;
import com.service.order.OrderService;
import com.service.order.OrderServiceImpl;
import com.service.product.ProductService;
import com.service.product.ProductServiceImpl;
import com.util.MyServlet;

@WebServlet("/order/*")
public class OrderServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	private final ProductRepository productRepository = new ProductRepositoryImpl();
	private final OrderRepository orderRepository = new OrderRepositoryImpl();
	
	private final CartRepository cartRepository = new CartRepositoryImpl();
	private final CartService cartService = new CartServiceImpl(cartRepository);
	
	private final ProductService productService = new ProductServiceImpl(productRepository);
	private final OrderService orderService = new OrderServiceImpl(orderRepository);
	
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

		// 1-1) 주문 폼
		// 1-2) 주문 폼 2 ( 상품 단품 구매시 )
		// 2) 주문 완료
		// 3) 주문 완료 -> order_ok로 이동
		if (uri.indexOf("order.do") != -1) {
			orderForm(req, resp);
		} else if (uri.indexOf("order-one.do") != -1) {
			orderOneForm(req, resp);
		} else if (uri.indexOf("order_ok.do") != -1) {
			orderSubmit(req, resp);
		} else if(uri.indexOf("order_complete.do")!= -1) {
			orderComplete(req,resp);
		}

	}

	private void orderForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1) 주문 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String message = null;
		
		try {
			
			String[] pi = req.getParameterValues("productIds");
			long[] products = null;
			products = new long[pi.length];
			for (int i = 0; i < pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}

			long memberId = info.getMemberId();
			
			List<Cart> list = cartService.transferCartList(memberId, products);
			Long totalPrice = 0L;
			Long dataCount= 0L;
			
			for (Cart c : list) {
				totalPrice += c.getPrice() * c.getQuantity();
				dataCount++;
			}

			req.setAttribute("dataCount", dataCount);
			req.setAttribute("message", message);
			req.setAttribute("list", list);
			req.setAttribute("totalPrice", totalPrice);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/order/order_list.jsp");
	}
	
	private void orderOneForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1-1) 주문 폼 2 ( 상품 단품 구매시 )
		try {
			Long productIds = Long.parseLong(req.getParameter("productIds"));
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			String productName = orderService.orderName(productIds);
			Long price = orderService.orderPrice(productIds);
			
			List<Cart> list = new ArrayList<>();
			Cart cart = new Cart();
			cart.setProductId(productIds);
			cart.setProductName(productName);
			cart.setPrice(price);
			cart.setQuantity(quantity);
			
			list.add(cart);
			
			Long totalPrice = orderService.orderPrice(productIds);
			Long dataCount = 1L;
			
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("list", list);
			req.setAttribute("totalPrice", totalPrice);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/order/order_list.jsp");
	}

	private void orderSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 2) 주문 완료
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		OrderBundle orderBundle = new OrderBundle();
		String cp = req.getContextPath();
		String message = null;
		String errorMessage=null;
		try {

			long memberId = info.getMemberId();

			orderBundle.setMemberId(memberId);
			orderBundle.setReceiveName(req.getParameter("receiveName"));

			String tel = req.getParameter("tel1") + "-" + req.getParameter("tel2") + "-" + req.getParameter("tel1");
			orderBundle.setTel(tel);

			orderBundle.setPostNum(req.getParameter("zip"));
			orderBundle.setAddress1(req.getParameter("zip1"));
			orderBundle.setAddress2(req.getParameter("zip2"));

			String[] pi = req.getParameterValues("items");
			long[] products = null;
			products = new long[pi.length];
			for (int i = 0; i < pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}

			List<Cart> list = cartService.transferCartList(memberId, products);

			List <OrderItem> itemlist = new ArrayList<OrderItem>();
			for (Cart c : list) {
				OrderItem orderItem = new OrderItem();
				long oneprice = orderService.orderPrice(c.getProductId());
				long price = oneprice * c.getQuantity();
				
				orderItem.setProductId(c.getProductId());
				orderItem.setQuantity(c.getQuantity());
				orderItem.setPrice(price);
				orderItem.setFinalPrice(price);
				
				itemlist.add(orderItem);
			}

			for(Cart c: list) {
				try {
					// 상품 초기화
					productService.subtractQuantity(c.getProductId(), c.getQuantity());
					
					long order_id = orderService.createOrderBundle(orderBundle, itemlist);
					
					// 장바구니에서 결제한 물품 초기화
					cartService.deleteCart(memberId, c.getProductId());
					
					resp.sendRedirect(cp+"/order/order_complete.do?order_id="+order_id);
					
				} catch (RuntimeException e) {
					System.out.println("재고보다 주문 수량이 많습니다.");
					resp.sendRedirect(cp+"/cart/list.do");
					return;
				}
            
			}
			req.setAttribute("errorMessage", errorMessage);
			req.setAttribute("message", message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void orderComplete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// // 3) 주문 완료 -> order_ok로 이동
		try {
			long orderId = Long.parseLong(req.getParameter("order_id"));
			long totalPrice = orderService.orderAllPrice(orderId);
			List<OrderItem> list2 = orderService.ListItems(orderId);
			
			req.setAttribute("totalPrice", totalPrice);
			req.setAttribute("orderId", orderId);
			req.setAttribute("list2", list2);
			
		}  catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/order/order_ok.jsp");
	}
	
	
}
