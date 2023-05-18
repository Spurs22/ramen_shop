package com.util;

import com.DTO.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	public static Member getMemberFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		Member member = null;
		if (sessionMember != null) {
			member = ((Member) sessionMember);
		}
		return member;
	}

	public static Long getMemberRollFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		Long memberRoll = null;
		if (sessionMember != null) {
			memberRoll = ((Member) sessionMember).getRoll();
		}
		return memberRoll;
	}

	public static Long getMemberIdFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		Long memberId = null;
		if (sessionMember != null) {
			memberId = ((Member) sessionMember).getMemberId();
		}
		return memberId;
	}

	public static String getMemberNickNameFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		String memberNickName = null;
		if (sessionMember != null) {
			memberNickName = ((Member) sessionMember).getNickName();
		}
		return memberNickName;
	}

	private static Object getSessionMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		return session.getAttribute("member");
	}
}
