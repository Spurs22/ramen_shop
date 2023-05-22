package com.servlet.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DTO.Member;
import com.DTO.SessionInfo;
import com.repository.member.MemberRepositoryImpl;
import com.util.MyServlet;
// 라멘 
@WebServlet("/member/*")
public class MemberServlet extends MyServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();


		// uri에 따른 작업 구분
		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
		   pwdSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
		   updateSubmit(req, resp);
		} else if (uri.indexOf("userIdCheck.do") != -1) {
			userIdCheck(req, resp);
		} else if (uri.indexOf("select.do") !=-1) {
			selectForm(req, resp);
		} else if(uri.indexOf("delete.do") !=-1) {
			deleteForm(req, resp);
		} else if(uri.indexOf("delete.ok") !=-1) {
			deleteSubmit(req, resp);
		}
	}

	// 로그인 
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}

	// 로그인 처리
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	
		HttpSession session = req.getSession();

		MemberRepositoryImpl repository = new MemberRepositoryImpl();
		String cp = req.getContextPath();

		
		try {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String userEmail = req.getParameter("email");
		String userPwd = req.getParameter("pwd");

		Member dto = repository.loginMember(userEmail, userPwd);
	     
		if (dto != null) {
			
			
	            // 로그인 성공 : 로그인 정보를 서버에 저장
	            // 세션의 유지 시간을 20분으로 설정 (기본 30분)
	            session.setMaxInactiveInterval(20 * 60);

	            // 세션에 저장할 내용
	            SessionInfo info = new SessionInfo();
	            info.setMemberId(dto.getMemberId());
	            info.setUserNickname(dto.getNickName());
	            info.setUserRoll(dto.getRoll());

	            // 세션에 member라는 이름으로 저장
	            session.setAttribute("member", info);
	            
	            // 메시지 출력 , 메인화면 리다이렉트
	            resp.setContentType("text/html; charset=UTF-8");
	            PrintWriter out = resp.getWriter();
	            out.println("<script>alert('반갑습니다!'); location.href='" + cp + "/';</script>");
	            out.flush();
	            
	            // 메인 화면으로 리다이렉트	    
	           //resp.sendRedirect(cp + "/");
	       
	        } else {
	            // 로그인 실패인 경우 (다시 로그인 폼으로)

	            String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
	            req.setAttribute("message", msg);

	            forward(req, resp, "/WEB-INF/views/member/login.jsp");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // 예외 처리
	    }
	}
	// 로그아웃
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		session.removeAttribute("member");

		session.invalidate();

		// 리다이렉트 루트
		resp.sendRedirect(cp + "/");
	}

	// 회원가입 폼
	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}

	// 회원가입 처리 
	
	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		MemberRepositoryImpl repository = new MemberRepositoryImpl();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String message = "";
		try {
			Member dto  = new Member();
			dto.setName(req.getParameter("name"));
			dto.setNickName(req.getParameter("nickName"));
			dto.setPassword(req.getParameter("password"));
			dto.setEmail(req.getParameter("email"));
			dto.setTel(req.getParameter("tel"));
            dto.setPostNum(req.getParameter("postNum"));
			dto.setAddress1(req.getParameter("address1"));
			dto.setAddress2(req.getParameter("address2"));

			repository.insertMember(dto);
	
			resp.sendRedirect(cp + "/");
			return;
		} catch (SQLException e) {
			if (e.getErrorCode() == 1)
				message = "이메일 중복으로 회원 가입이 실패 했습니다.";
			else if (e.getErrorCode() == 1400)
				message = "필수 사항을 입력하지 않았습니다.";
			else
				message = "회원 가입이 실패 했습니다.";
			// 기타 - 2291:참조키 위반, 12899:폭보다 문자열 입력 값이 큰경우
		} catch (Exception e) {
			message = "회원 가입이 실패 했습니다.";
			e.printStackTrace();
		}

		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		req.setAttribute("message", message);
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		
	}

	// 패스워드 확인 폼
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (info == null) {
			// 로그 아웃 상태이면
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String mode = req.getParameter("mode");
		if (mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
	}

	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인
		MemberRepositoryImpl repository = new MemberRepositoryImpl();
		HttpSession session = req.getSession();
		
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			// Long memberId = info.getMemberId(); // memberId를 Long 타입으로 가져옴
	        Member dto = repository.readMember(info.getMemberId());
	        
			if (dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}

			String userPwd = req.getParameter("password");
			String mode = req.getParameter("mode");
			
			if(mode == null) {
	            // mode가 null일 경우 예외 처리
	            throw new IllegalArgumentException("mode 파라미터가 없어요");
	        }
			
			if (!dto.getPassword().equals(userPwd)) {
				if (mode.equals("update")) {
					req.setAttribute("title", "회원 정보 수정");
				} else {
					req.setAttribute("title", "회원 탈퇴");
				}

				req.setAttribute("mode", mode);
				req.setAttribute("message", "패스워드가 일치하지 않습니다.");
				forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
				return;
			}

			if (mode.equals("delete")) {
				// 회원탈퇴
				repository.deleteMember((info.getMemberId()));

				session.removeAttribute("member");
				session.invalidate();

				resp.sendRedirect(cp + "/");
				return;
			}

			// 회원정보수정 - 회원수정폼으로 이동
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	
	}

	// 회원정보 수정 완료
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		
		MemberRepositoryImpl repository = new MemberRepositoryImpl();
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
	    	SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			Member dto = new Member();
			
			
	        dto.setMemberId(info.getMemberId()); 
			dto.setPassword(req.getParameter("password"));
			dto.setNickName(req.getParameter("nickName"));
			dto.setEmail(req.getParameter("email"));
			dto.setTel(req.getParameter("tel"));
			dto.setPostNum(req.getParameter("postNum"));
			dto.setAddress1(req.getParameter("address1"));
			dto.setAddress2(req.getParameter("address2"));

			repository.updateMember(dto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}

	protected void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	protected void selectForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward(req, resp, "/WEB-INF/views/member/select.jsp");
	}
	
	protected void selectSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
	    // 회원탈퇴 폼
	 protected void deleteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession();
	    SessionInfo info = (SessionInfo) session.getAttribute("member");

	    if (info == null) {
	        resp.sendRedirect(req.getContextPath() + "/member/login.do");
	        return;
	    }

	    req.setAttribute("title", "회원 탈퇴");
	    forward(req, resp, "/WEB-INF/views/member/delete.jsp");
	}
		
		// 회원 탈퇴 처리 
		private void deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			
			MemberRepositoryImpl repository = new MemberRepositoryImpl();
			HttpSession session = req.getSession();
			String cp = req.getContextPath();
			
	    try {
	    	SessionInfo info = (SessionInfo) session.getAttribute("member");
	        if (info == null) { // 로그아웃 된 경우
	            resp.sendRedirect(cp + "/member/login.do");
	            return;
	        }

            
	        long memberId = info.getMemberId();
	        
	        repository.deleteMember(memberId);
	        
	        req.setAttribute("message", "회원 탈퇴가 완료되었습니다.");
	        req.getRequestDispatcher("/index.jsp").forward(req, resp);

	        
		} 
	    
	    
	    catch (Exception e) {
			e.printStackTrace();
			
			req.setAttribute("error", "회원 탈퇴에 실패했습니다.");
	        req.getRequestDispatcher("/delete.jsp").forward(req, resp);
		}

		resp.sendRedirect(cp + "/");
	}
		
	
}
