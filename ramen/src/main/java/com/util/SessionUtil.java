package com.util;

import com.DTO.Member;
import com.DTO.SessionInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	public static SessionInfo getSessionFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		SessionInfo sessionInfo = null;
		if (sessionMember != null) {
			sessionInfo = ((SessionInfo) sessionMember);
		}
		return sessionInfo;
	}

	public static Long getMemberRollFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		Long memberRoll = null;
		if (sessionMember != null) {
			memberRoll = ((SessionInfo) sessionMember).getUserRoll();
		}
		return memberRoll;
	}

	public static Long getMemberIdFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		Long memberId = null;
		if (sessionMember != null) {
			memberId = ((SessionInfo) sessionMember).getMemberId();
		}
		return memberId;
	}

	public static String getMemberNickNameFromSession(HttpServletRequest req) {
		Object sessionMember = getSessionMember(req);

		String memberNickName = null;
		if (sessionMember != null) {
			memberNickName = ((SessionInfo) sessionMember).getUserNickname();
		}
		return memberNickName;
	}

	private static Object getSessionMember(HttpServletRequest req) {
		HttpSession session = req.getSession();
		return session.getAttribute("member");
	}
}
