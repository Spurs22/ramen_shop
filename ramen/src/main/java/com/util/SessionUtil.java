package com.util;

import com.DTO.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	public static Member getMemberFromSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object sessionMember = session.getAttribute("member");

		Member member = null;
		if (sessionMember != null) {
			member = ((Member) sessionMember);
		}
		return member;
	}
}
