package com.repository.orderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.OrderBundle;
import com.DTO.OrderItem;
import com.DTO.OrderStatistics;
import com.util.DBConn;
import com.util.DBUtil;

public class OrderDetailRepositoryImpl implements OrderDetailRepository{

private Connection conn = DBConn.getConnection();

	// 송장번호 등록
	@Override
	public void setDeliveryNumber(Long orderId, Long deliveryId) {
		// 송장번호 등록
		// 테이블 : order_bundle 컬럼: delivery_id
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE order_bundle "
					+ " SET delivery_id = ? "
					+ " WHERE id = ?" ;
			
			pstmt = conn.prepareStatement(sql);
			 
			pstmt.setLong(1, deliveryId);
			pstmt.setLong(2, orderId);
		
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	// 전체 주문내역 확인 >> orderBundle 데이터만 출력
	@Override
	public List<OrderBundle> findOrderAll() {
		List<OrderBundle> OrderBundleList = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 주문번호, 맴버아이디, 송장번호, 결제일, 받는분, 전화번호, 우편번호, 주소1, 주소2
			sb.append("SELECT b.id, a.id, b.delivery_id, b.created_date, ");
			sb.append(" b.receive_name, b.tel, b.post_num, b.address1, b.address2, a.email ");
			sb.append(" FROM member a, order_bundle b ");
			sb.append(" WHERE a.id = b.member_id ");
			sb.append(" ORDER BY b.created_date DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderBundle ob = new OrderBundle();
				
				// 주문번호, 맴버아이디, 송장번호, 결제일, 받는분, 전화번호, 우편번호, 주소1, 주소2
				ob.setOrderBundleId(rs.getLong("b.id"));
				ob.setMemberId(rs.getLong("a.id"));
				ob.setDeliveryId(rs.getLong("delivery_id"));
				ob.setCreatedDate(rs.getString("created_date"));
				ob.setReceiveName(rs.getString("receive_name"));
				ob.setTel(rs.getString("tel"));
				ob.setPostNum(rs.getString("post_num"));
				ob.setAddress1(rs.getString("address1"));
				ob.setAddress2(rs.getString("address2"));
				ob.setUserEmail(rs.getString("email"));
				
				OrderBundleList.add(ob);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return OrderBundleList;
	}

	// 회원 > 주문내역 확인
	@Override
	public List<OrderBundle> findOrderByMemberId(Long memberId) {
		// 회원 > 주문내역 확인
		// 맴버아이디를 받아와서 맴버별 주문내역 확인하기(마이페이지에 보이는 내역)
		List<OrderBundle> list = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			//주문번호, 처리상태, 주문일자, 주문상세번호, 상품아이디, 상품명, 수량, 정가, 최종가격, 유저이메일, 송장번호, 받는 분, 전화번호, 우편번호, 주소1, 주소2
			sb.append("SELECT b.id, d.status_name, b.created_date, c.id, c.product_id, e.name, ");
			sb.append(" c.quantity, c.price, c.final_price, a.email, b.delivery_id, ");
			sb.append(" b.receive_name, b.tel, b.post_num, b.address1, b.address2 ");
			sb.append(" FROM member a, order_bundle b, order_item c, order_status d, product e");
			sb.append(" WHERE a.email = ?"); // 이메일로 해야할지 맴버 아이디로 해야할지.. 마이페이지 보이는 내역과 관리자페이지 주문리스트에서 검색했을 때 모두 고려해야함
			sb.append(" AND b.id = c.order_id");
			sb.append(" AND c.status_id = d.id");
			sb.append(" AND c.product_id = e.id");
			sb.append(" ORDER BY b.created_date DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, memberId); // 이메일 받아서 회원주문리스트만 출력하기
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				/*
				OrderBundle odto = new OrderBundle();
				
				//주문번호, 처리상태, 주문일자, 주문상세번호, 상품아이디, 상품명, 수량, 정가, 최종가격, 유저이메일, 송장번호, 받는 분, 전화번호, 우편번호, 주소1, 주소2
				odto.setOrderBundleId(rs.getLong("b.id"));
				//odto.setStatusName(rs.getLong("status_name"));
				odto.setCreatedDate(rs.getString("created_date"));
				odto.setOrderItemId(rs.getLong("c.id"));
				odto.setProductId(rs.getLong("product_id"));
				//odto.setProductName(rs.getLong("name"));
				odto.setQuantity(rs.getInt("quantity"));
				odto.setPrice(rs.getLong("price"));
				odto.setFinalPrice(rs.getLong("final_price"));
				//odto.setEmail(rs.getLong("email")); // 유저 이메일
				odto.setDeliveryId(rs.getLong("delivery_id"));
				odto.setReceiveName(rs.getString("receive_name"));
				odto.setTel(rs.getString("tel"));
				odto.setPostNum(rs.getString("post_num"));
				odto.setAddress1(rs.getString("address1"));
				odto.setAddress2(rs.getString("address2"));
				
				list.add(odto);
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return list;
	}
	
	// 상태별 주문내역 확인
	@Override
	public List<OrderBundle> findOrderByStatusId(Long orderStatusId) {
		// 상태(1.결제완료 2.배송중 3.배송완료 4.주문취소) > 주문내역 확인
		// 주문상태 받아와서 주문내역 확인하기(관리자페이지에서 확인 가능)
		List<OrderBundle> list = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			//주문번호, 처리상태, 주문일자, 주문상세번호, 상품아이디, 상품명, 수량, 정가, 최종가격, 유저이메일, 송장번호, 받는 분, 전화번호, 우편번호, 주소1, 주소2
			sb.append("SELECT b.id, d.status_name, b.created_date, c.id, c.product_id, e.name, ");
			sb.append(" c.quantity, c.price, c.final_price, a.email, b.delivery_id, ");
			sb.append(" b.receive_name, b.tel, b.post_num, b.address1, b.address2 ");
			sb.append(" FROM member a, order_bundle b, order_item c, order_status d, product e");
			sb.append(" WHERE c.status_id = ?");
			sb.append(" AND b.id = c.order_id");
			sb.append(" AND c.status_id = d.id");
			sb.append(" ORDER BY b.created_date DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, orderStatusId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				/*
				OrderBundle odto = new OrderBundle();
				
				odto.setOrderBundleId(rs.getLong("b.id"));
				//odto.setStatusName(rs.getLong("status_name"));
				odto.setCreatedDate(rs.getString("created_date"));
				odto.setOrderItemId(rs.getLong("c.id"));
				odto.setProductId(rs.getLong("product_id"));
				//odto.setProductName(rs.getLong("name"));
				odto.setQuantity(rs.getInt("quantity"));
				odto.setPrice(rs.getLong("price"));
				odto.setFinalPrice(rs.getLong("final_price"));
				//odto.setEmail(rs.getLong("email")); // 유저 이메일
				odto.setDeliveryId(rs.getLong("delivery_id"));
				odto.setReceiveName(rs.getString("receive_name"));
				odto.setTel(rs.getString("tel"));
				odto.setPostNum(rs.getString("post_num"));
				odto.setAddress1(rs.getString("address1"));
				odto.setAddress2(rs.getString("address2"));
				
				list.add(odto);
				*/
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		
		return list;
	}

	// 주문번호 > 주문내역 확인
	@Override
	public OrderBundle findOrderByOrderId(Long orderId) {
		// 주문번호 > 주문내역 확인
		OrderBundle odto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			//주문번호, 처리상태, 주문일자, 주문상세번호, 상품아이디, 상품명, 수량, 정가, 최종가격, 유저이메일, 송장번호, 받는 분, 전화번호, 우편번호, 주소1, 주소2
			sb.append("SELECT b.id, d.status_name, b.created_date, c.id, c.product_id, e.name, ");
			sb.append(" c.quantity, c.price, c.final_price, a.email, b.delivery_id, ");
			sb.append(" b.receive_name, b.tel, b.post_num, b.address1, b.address2 ");
			sb.append(" FROM member a, order_bundle b, order_item c, order_status d");
			sb.append(" WHERE a.id = b.member_id");
			sb.append(" AND b.id = ? ");
			sb.append(" AND c.status_id = d.id");
			sb.append(" ORDER BY b.created_date DESC ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, orderId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				/*
				odto = new OrderBundle();
				
				
				odto.setOrderBundleId(rs.getLong("b.id"));
				//odto.setStatusName(rs.getLong("status_name"));
				odto.setCreatedDate(rs.getString("created_date"));
				odto.setOrderItemId(rs.getLong("c.id"));
				odto.setProductId(rs.getLong("product_id"));
				//odto.setProductName(rs.getLong("name"));
				odto.setQuantity(rs.getInt("quantity"));
				odto.setPrice(rs.getLong("price"));
				odto.setFinalPrice(rs.getLong("final_price"));
				//odto.setEmail(rs.getLong("email")); // 유저 이메일
				odto.setDeliveryId(rs.getLong("delivery_id"));
				odto.setReceiveName(rs.getString("receive_name"));
				odto.setTel(rs.getString("tel"));
				odto.setPostNum(rs.getString("post_num"));
				odto.setAddress1(rs.getString("address1"));
				odto.setAddress2(rs.getString("address2"));
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return odto;
	}

	// 데이터 개수
	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM order_item";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return result;
	}
		
	// 검색에서의 데이터 개수
	  //1) 전체 주문리스트
	  //2) 회원 주문리스트  -- 회원 검색
	  //3) 주문상태별 주문리스트 -- 주문상태별 검색
	  //4) 주문번호별 내역 -- 주문번호별 검색
	@Override
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM order_item oi ";
				//+ " JOIN member1 m ON b.userId = m.userId "; // 맴버테이블, 주문상태 테이블, 번들테이블 조인하기
				
			if (condition.equals("memberId")) { 
				// 회원이메일 검색
				sql += "  WHERE email = ?  "; 
				
			} else if (condition.equals("status_name")) { 
				// 주문상태별 검색
				sql += "  WHERE status_name = ? ";
				
			} else { 
				// 주문번호별 검색
				sql += "  WHERE orderBundleId = ? ";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
			}

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return result;
	}
	
	// 검색에서의 주문리스트
	@Override
	public List<OrderBundle> listBoard(int offset, int size, String condition, String keyword) {
		List<OrderBundle> list = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
/*  
  1) 전체 주문리스트
  2) 회원 주문리스트  -- 회원 검색
  3) 주문상태별 주문리스트 -- 주문상태별 검색
  4) 주문번호별 내역 -- 주문번호별 검색
*/
		try {
			// 주문번호, 처리상태, 주문일자, 주문상세번호, 상품아이디, 상품명, 수량, 정가, 최종가격, 유저이메일, 송장번호, 받는 분, 전화번호, 우편번호, 주소1, 주소2
			sb.append("SELECT TOb.id, d.status_name, b.created_date, c.id, c.product_id, e.name, ");
			sb.append(" c.quantity, c.price, c.final_price, a.email, b.delivery_id, ");
			sb.append(" b.receive_name, b.tel, b.post_num, b.address1, b.address2 ");
			sb.append(" FROM  order_item oi ");
			//sb.append(" JOIN member1 m ON b.userId = m.userId ");
			if (condition.equals("memberId")) { 
				// 회원이메일 검색
				sb.append( " WHERE email = ?  "); 
				
			} else if (condition.equals("status_name")) { 
				// 주문상태별 검색
				sb.append( "  WHERE status_name = ? ");
				
			} else { 
				// 주문번호별 검색
				sb.append( "  WHERE orderBundleId = ? ");
			}
			sb.append(" ORDER BY num DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				/*
				Order dto = new Order();
				
					dto.setNum(rs.getLong("num"));
					dto.setUserName(rs.getString("userName"));
					dto.setSubject(rs.getString("subject"));
					dto.setHitCount(rs.getInt("hitCount"));
					dto.setReg_date(rs.getString("reg_date"));
				 
				list.add(dto);
				*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}

		return list;
	}
	
	// 기간별 매출 통계
	@Override
	public OrderStatistics SalesStatisticsByPeriod(String createdDate) {
		// 1일 | 1개월 | 6개월 | 1년 | 전체
		OrderStatistics odto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			// 결제완료, 배송중, 배송완료인 경우에만 출력하기 (주문취소일 때는 불러오지 않기!)
			// 총판매수량, 총 정가, 총매출액
			// TO_CHAR로 바꿔보기
			sb.append("SELECT sum(a.quantity) as 총판매수량, sum(a.price) as 총정가, sum(a.final_price) as 총매출액 ");
			sb.append(" FROM ORDER_BUNDLE b");
			/*
			if() { // 전체
				sb.append(" JOIN ORDER_item a ON a.order_id = b.id");
			} else if(){ // 1일
				sb.append(" JOIN ORDER_item a ON a.order_id = b.id");
				sb.append(" WHERE created_date >= TO_CHAR(SYSDATE -1)");
			} else if() { // 1개월
				sb.append(" JOIN ORDER_item a ON a.order_id = b.id");
				sb.append(" WHERE created_date >= ADD_MONTHS(SYSDATE, -1)");
			} else if() { // 6개월
				sb.append(" JOIN ORDER_item a ON a.order_id = b.id");
				sb.append(" WHERE created_date >= ADD_MONTHS(SYSDATE, -6)");
			} else if() { // 12개월
				sb.append(" JOIN ORDER_item a ON a.order_id = b.id");
				sb.append(" WHERE created_date >= ADD_MONTHS(SYSDATE, -12)");
			}
			*/
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				/*
				odto = new OrderBundle();
				
				odto.setOrderId(rs.getLong("c.order_id"));
				odto.setMemberId(rs.getLong("a.id"));
				odto.setOrderBundleId(rs.getLong("b.id"));
				odto.setProductId(rs.getLong("product_id"));
				odto.setStatusId(rs.getLong("status_id"));
				odto.setQuantity(rs.getInt("quantity"));
				odto.setFinalPrice(rs.getLong("final_price"));
				odto.setCreatedDate(rs.getString("created_date"));
				odto.setDeliveryId(rs.getLong("delivery_id"));
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return odto;
		
	}

	// 상품별 매출 통계
	@Override
	public OrderStatistics SalesStatisticsByProduct(Long finalprice, Long productId) {
		// 품목별 아이디 받아오기
		OrderStatistics odto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 
			sb.append("SELECT b.product_id, a.name, sum(b.quantity) as 판매수량, ");
			sb.append(" sum(b.price) as 정가, sum(b.final_price) as 최종매출액 ");
			sb.append(" FROM order_item b ");
			sb.append(" JOIN product a ON a.id = b.product_id ");
			sb.append(" WHERE b.status_id <= 3");
			sb.append(" GROUP BY b.product_id, a.name");
			sb.append(" ORDER BY product_id ");

			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				/*
				odto = new OrderBundle();
				
				odto.setOrderId(rs.getLong("b.product_id"));
				odto.setMemberId(rs.getLong("a.name"));
				odto.setOrderBundleId(rs.getLong("b.quantity"));
				odto.setProductId(rs.getLong("b.price"));
				odto.setStatusId(rs.getLong("b.final_price"));
				*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return odto;
	}

}
