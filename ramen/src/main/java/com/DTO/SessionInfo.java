package com.DTO;

	public class SessionInfo {
		private Long memberId;
		private String userNickname;
		private long userRoll;
		
		public String getUserNickname() {
			return userNickname;
		}
		public void setUserNickname(String userNickname) {
			this.userNickname = userNickname;
		}
		public Long getMemberId() {
			return memberId;
		}
		public void setMemberId(Long memberId) {
			this.memberId = memberId;
		}
		public long getUserRoll() {
			return userRoll;
		}
		public void setUserRoll(long userRoll) {
			this.userRoll = userRoll;
		}
	}
