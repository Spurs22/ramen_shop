package com.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Cart;
import com.DTO.Product;
import com.DTO.SessionInfo;
import com.repository.cart.CartRepositoryImpl;
import com.repository.product.ProductRepository;
import com.repository.product.ProductRepositoryImpl;
import com.service.product.ProductService;
import com.service.product.ProductServiceImpl;
import com.util.MyServlet;

@WebServlet("/cart/*")
public class CartServlet extends MyServlet {
   private static final long serialVersionUID = 1L;
   private final ProductRepository productRepository = new ProductRepositoryImpl();
   
   private final CartRepositoryImpl cartRepositoryImpl = new CartRepositoryImpl();
   private final ProductService productService = new ProductServiceImpl(productRepository);
   
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
      // 2) 장바구니의 물건 수량을 변경한다.
      // 3) 장바구니의 물건 리스트를 삭제한다.
      // 4) 장바구니의 물건을 삭제한다.
      if (uri.indexOf("list.do") != -1) {
         cartList(req, resp);
      } else if (uri.indexOf("num_update.do") != -1) {
         cartNumUpdate(req, resp);
      } else if(uri.indexOf("list_delete.do")!= -1) {
    	  cartListDelete(req,resp);
      } else if(uri.indexOf("delete.do")!= -1) {
			cartDelete(req,resp);
      } 
   }

   protected void cartList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // 1) 장바구니를 본다. - 30일 이후 지나면 삭제
      HttpSession session = req.getSession();
      SessionInfo info = (SessionInfo) session.getAttribute("member");

      CartRepositoryImpl cartRepositoryImpl = new CartRepositoryImpl();
      
      try {
         // 장바구니 30일 이후 품목 삭제
         cartRepositoryImpl.deleteAutoCart();

         Long memberId = info.getMemberId();
         List<Cart> list = cartRepositoryImpl.findCartByMemberId(memberId);

         // 장바구니 총 개수 구하기
         int dataCount = cartRepositoryImpl.getCnt(memberId);
         
         req.setAttribute("list", list);
         req.setAttribute("dataCount", dataCount);

      } catch (Exception e) {
         e.printStackTrace();
      }

      forward(req, resp, "/WEB-INF/views/cart/cart_list.jsp");
   }

   protected void cartNumUpdate(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
      // 2) 장바구니의 물건 수량을 변경한다.
      System.out.println("수량 변경");
      
      HttpSession session = req.getSession();
      SessionInfo info = (SessionInfo) session.getAttribute("member");
      String cp = req.getContextPath();

      try {
         Long memberId = info.getMemberId();
         
         int num = Integer.parseInt(req.getParameter("quantity"));
         Long productId = Long.parseLong(req.getParameter("productId"));
         
         List<Cart> list = cartRepositoryImpl.findCartByMemberId(memberId);

         // 장바구니 총 개수 구하기
         int dataCount = cartRepositoryImpl.getCnt(memberId);
         
         for (Cart c : list) {
            // 잔여수량 체크
            Product product = productService.findProductByProductId(c.getProductId());
            
            if(product.getRemainQuantity() >= c.getQuantity()) { 
               cartRepositoryImpl.editItemNum(productId, memberId, num);
            }
         }
         
         req.setAttribute("dataCount", dataCount);

      } catch (Exception e) {
         e.printStackTrace();
      }
      resp.sendRedirect(cp + "/cart/list.do");
      
   }
   
	protected void cartListDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 3) 장바구니의 물건 리스트를 취소한다.
		System.out.println("장바구니 리스트 취소");
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		try {
			
			String[] pi = req.getParameterValues("productIds");
			long[] products = null;
			products = new long[pi.length];
			for(int i=0; i<pi.length; i++) {
				products[i] = Long.parseLong(pi[i]);
			}
			
			CartRepositoryImpl cri = new CartRepositoryImpl();
			Long memberId = info.getMemberId();
			cri.deleteCartList(memberId, products);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/cart/list.do");
	}
	
	protected void cartDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 4) 장바구니의 물건을 취소한다.
		System.out.println("장바구니 아이템 취소");
		
		CartRepositoryImpl cri = new CartRepositoryImpl();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			Long memberId = info.getMemberId();
			Long productId =Long.parseLong(req.getParameter("productId"));
			
			cri.deleteCart(memberId, productId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/cart/list.do");
	}
}   
  