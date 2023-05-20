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
			sql = "SELECT NVL(COUNT(*), 0) FROM order_bundle WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
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
		StringBuilder sb = new StringBuilder();
		
		try {
			// 주문번호(ob.id), 회원번호(m.id), 송장번호(ob.delivery_id), 결제일(ob.created_date), 받는분(ob.receive_name),
			// 전화번호(ob.tel), 우편번호(ob.post_num), 주소1(ob.address1), 주소2(ob.address2), 회원이메일(m.email)
			// 주문상태(os.status_name), 총 결제 금액(sum(oi.final_pirce)
			sb.append("SELECT ob.id, m.id, ob.delivery_id, ob.created_date, ob.receive_name, ");
			sb.append("   ob.tel, ob.post_num, ob.address1, ob.address2, m.email, os.status_name, SUM(oi.final_price) as total_price ");
			sb.append("   FROM member m ");
			sb.append("   INNER JOIN order_bundle ob ON m.id = ob.member_id ");
			sb.append("   INNER JOIN order_item oi ON ob.id = oi.order_id ");
			sb.append("   INNER JOIN order_status os ON oi.status_id = os.id ");
			sb.append("   WHERE m.id = ? ");
			sb.append("   GROUP BY ob.id, m.id, ob.delivery_id, ob.created_date, ob.receive_name, ob.tel, ob.post_num, ob.address1, ob.address2, m.email, os.status_name ");
			sb.append("   ORDER BY ob.created_date DESC ");
			sb.append("   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderBundle dto = new OrderBundle();
				
				dto.setOrderBundleId(rs.getLong(1));
				dto.setMemberId(rs.getLong(2));
				dto.setDeliveryId(rs.getLong(3));
				dto.setCreatedDate(rs.getString(4));
				dto.setReceiveName(rs.getString(5));
				dto.setTel(rs.getString(6));
				dto.setPostNum(rs.getString(7));
				dto.setAddress1(rs.getString(8));
				dto.setAddress2(rs.getString(9));
				dto.setUserEmail(rs.getString(10));
				dto.setStatusName(rs.getString(11));
				dto.setTotalPrice(rs.getLong(12));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public OrderBundle findOrderDetail(Long memeberId, int offset, int size) throws SQLException {
		return null;
	}

}
