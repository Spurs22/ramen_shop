package com.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
	public static void closeResource(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public static void closeResource(PreparedStatement pstmt, ResultSet rs) {
		closeResource(pstmt);

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
