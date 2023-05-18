package com.repository.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.OrderBundle;
import com.util.DBConn;
import com.util.DBUtil;

public class MypageOrderRepositoryImpl implements MypageOrderRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int dataCount(Long memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM order_item WHERE member_id = ?";
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}

	@Override
	public List<OrderBundle> findOrderAll(Long memberId, int offset, int size) throws SQLException {
		List<OrderBundle> list = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 주문번호(ob.id), 회원번호(m.id), 송장번호(ob.delivery_id), 결제일(ob.created_date), 받는분(ob.receive_name),
			// 전화번호(ob.tel), 우편번호(ob.post_num), 주소1(ob.address1), 주소2(ob.address2), 회원이메일(m.email)
			// 총 결제 금액(sum(oi.final_pirce)
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	@Override
	public OrderBundle findOrderDetail(Long memeberId, int offset, int size) throws SQLException {
		return null;
	}

}
