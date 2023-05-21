package com.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Cart;
import com.DTO.RecipeProduct;
import com.DTO.SessionInfo;
import com.repository.cart.CartRepository;
import com.repository.cart.CartRepositoryImpl;
import com.service.cart.CartService;
import com.service.cart.CartServiceImpl;
import com.util.MyServlet;

@WebServlet("/cart/*")
public class CartServlet extends MyServlet {
   private static final long serialVersionUID = 1L;
   private final CartRepository cartRepository = new CartRepositoryImpl();
   private final CartService cartService = new CartServiceImpl(cartRepository);
   
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
      
      // 1) 장바구니에 물건을 추가한다. ( 레시피 조합 > 장바구니 추가 )
      // 2) 장바구니를 본다. - 30일 이후 지나면 삭제
      // 3) 장바구니의 물건 수량을 변경한다.
      // 4) 장바구니의 물건 리스트를 삭제한다.
      // 5) 장바구니의 물건을 삭제한다.
      if (uri.indexOf("recipe_add.do") != -1) {
          recipeAdd(req, resp);
      } else if (uri.indexOf("list.do") != -1) {
         cartList(req, resp);
      } else if (uri.indexOf("num_update.do") != -1) {
         cartNumUpdate(req, resp);
      } else if(uri.indexOf("list_delete.do")!= -1) {
    	  cartListDelete(req,resp);
      } else if(uri.indexOf("delete.do")!= -1) {
			cartDelete(req,resp);
      } 
   }

   protected void recipeAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	// 1) 장바구니에 물건을 추가한다. ( 레시피 조합 > 장바구니 추가 )
	   	HttpSession session = req.getSession();
	    SessionInfo info = (SessionInfo) session.getAttribute("member");
	    
	    try {
	         Long memberId = info.getMemberId();
	         
	         // 해당 멤버의 상품리스트...
	        
	         // List<RecipeProduct> list = 레시피조합상품리스트.;
	         List<RecipeProduct> list = null;
	         
	         for(RecipeProduct p:list) {
	        	 cartService.createItem(p.getProductId(), memberId, p.getQuantity());
	         }

	         // 장바구니 총 개수 구하기
	         int dataCount = cartService.getCnt(memberId);
	         
	         req.setAttribute("list", list);
	         req.setAttribute("dataCount", dataCount);

	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	      forward(req, resp, "/WEB-INF/views/cart/cart_list.jsp");
   }

	protected void cartList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // 2) 장바구니를 본다. - 30일 이후 지나면 삭제
      HttpSession session = req.getSession();
      SessionInfo info = (SessionInfo) session.getAttribute("member");

      try {
         Long memberId = info.getMemberId();
         
         // 해당 멤버의 장바구니 목록 조회
         List<Cart> list = cartService.findCartByMemberId(memberId);
         
         for(Cart c : list) {
        	 if(c.getRemainQuantity() < c.getQuantity()) {
        		 cartService.editItemNum(c.getProductId(), memberId, c.getRemainQuantity());
        	 }
         }
         
         list = cartService.findCartByMemberId(memberId);
         
         // 장바구니 총 개수 구하기
         int dataCount = cartService.getCnt(memberId);
         
         req.setAttribute("list", list);
         req.setAttribute("dataCount", dataCount);

      } catch (Exception e) {
         e.printStackTrace();
      }

      forward(req, resp, "/WEB-INF/views/cart/cart_list.jsp");
   }

   protected void cartNumUpdate(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
      // 3) 장바구니의 물건 수량을 변경한다.
      System.out.println("수량 변경");
      
      HttpSession session = req.getSession();
      SessionInfo info = (SessionInfo) session.getAttribute("member");
      String cp = req.getContextPath();

      try {
         Long memberId = info.getMemberId();
         
         // JSP에서 전달받은 값
         int num = Integer.parseInt(req.getParameter("quantity"));
         Long productId = Long.parseLong(req.getParameter("productId"));

         // 장바구니 총 개수 구하기
         int dataCount = cartService.getCnt(memberId);
         
         cartService.editItemNum(productId, memberId, num);
         
         req.setAttribute("dataCount", dataCount);

      } catch (Exception e) {
         e.printStackTrace();
      }
      resp.sendRedirect(cp + "/cart/list.do");
   }
   
	protected void cartListDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 4) 장바구니의 물건 리스트를 취소한다.
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
			
			Long memberId = info.getMemberId();
			cartService.deleteCartList(memberId, products);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/cart/list.do");
	}
	
	protected void cartDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 5) 장바구니의 물건을 취소한다.
		System.out.println("장바구니 아이템 취소");
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			Long memberId = info.getMemberId();
			Long productId =Long.parseLong(req.getParameter("productId"));
			
			cartService.deleteCart(memberId, productId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/cart/list.do");
	}

}   
  