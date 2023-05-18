package com.util;

import com.DTO.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	public static Long getMemberIdFromSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object sessionMember = session.getAttribute("member");

		Long memberId = null;
		if (sessionMember != null) {
			memberId = ((Member) sessionMember).getMemberId();
		}
		return memberId;
	}
}
