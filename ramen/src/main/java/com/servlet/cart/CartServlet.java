package com.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DTO.Cart;
import com.repository.cart.CartRepositoryImpl;
import com.util.MyServlet;




@WebServlet("/cart/*")
public class CartServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		/*
		// 세션 정보
		HttpSession session = req.getSession();
		// >> 로그인 구현하고나서 변경될 가능성 있음 ㅇㅇ
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info == null) {
			// forward(req,resp,"/WEB-INF/views/member/login.jsp");
			return;
		}
		*/
		
		// 1) 장바구니를 본다. - 30일 이후 지나면 삭제
		// 2) 장바구니의 물건을 선택 > 주문
		// 3) 장바구니의 물건을 취소한다.
		// 4) 장바구니의 물건 리스트를 취소한다.
		if(uri.indexOf("cart_list.do")!= -1) {
			listCart(req,resp);
		} else if(uri.indexOf("cart_select.do")!= -1) {
			selectItem(req,resp);
		} else if(uri.indexOf("cart_cancel.do")!= -1) {
			cancelItem(req,resp);
		} else if(uri.indexOf("cartlist_cancel.do")!= -1) {
			cancelList(req,resp);
		}
	}


	protected void listCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1) 장바구니를 본다. - 30일 이후 지나면 삭제
		System.out.println("장바구니 리스트");
		
		CartRepositoryImpl cri = new CartRepositoryImpl();
		String cp = req.getContextPath();
		
		try {
			
			// 장바구니 30일 이후 품목 삭제
			cri.deleteAutoCart();
			
			List<Cart> list = null;
			
			// memberId 세션과 비교해줘야 함.
			Long memberId = (long) 1;
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
	
	protected void selectItem(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
		// 2) 장바구니의 물건을 선택 > 주문
		System.out.println("장바구니 물건 선택 > 주문");
	}
	
	protected void cancelItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 3) 장바구니의 물건을 취소한다.
		System.out.println("장바구니 아이템 취소");
		
		CartRepositoryImpl cri = new CartRepositoryImpl();
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			// member 구현 후 수정.. member 확인.... 
			Long memberId = (long) 1;
			Long productId = (long) 2;
			// Long productId = req.getParameter("productId"); // 여러개... 가져와서 jsp 작성후 확인
			
			cri.deleteCart(memberId, productId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void cancelList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 4) 장바구니의 물건 리스트를 취소한다.
		System.out.println("장바구니 리스트 취소");
		
		// HttpSession session = req.getSession();
		// SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		try {
			
			String[] pi = req.getParameterValues("productIds");
			long products[] = null;
			products = new long[pi.length];
			for(int i=0; i<pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}
			
			CartRepositoryImpl cri = new CartRepositoryImpl();
			
			// 아이디 ..
			long memberId = (long)1;
			
			// 상품 삭제
			cri.deleteCartList(memberId, products);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/cart/cart_list.do?");
	}
	
}
