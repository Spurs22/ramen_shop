package com.repository.admin;

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
					+ " SET status_id = 2  "
					+ " WHERE delivery_id = ?" ;
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

	// 전체 주문내역 확인 >> orderBundle 데이터만 출력 (OrderBundle 내 OrderItem List 출력X)
	@Override
	public List<OrderBundle> findOrderAll(int offset, int size, int statusId) {
		List<OrderBundle> orderBundleList = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 주문상태, 송장번호, 합계
			sb.append("SELECT DISTINCT b.id as orderbundleid, b.created_date, a.email, b.tel, ");
			sb.append(" b.receive_name, d.status_name, b.delivery_id, NVL(tot,0) tot ");
			sb.append(" FROM member a ");
			sb.append(" JOIN order_bundle b ON a.id = b.member_id ");
			sb.append(" JOIN order_item c ON b.id = c.order_id ");
			sb.append(" JOIN order_status d ON c.status_id = d.id ");
			sb.append(" LEFT OUTER JOIN( ");
			sb.append("       SELECT order_id, sum(final_price) tot FROM order_item  GROUP BY order_id ");
			sb.append(" ) s ON c.order_id = s.order_id ");
			
			// status주문상태 검색 조건
			if(statusId == 1) {
				sb.append(" WHERE d.id = 1");
			} else if(statusId == 2) {
				sb.append(" WHERE d.id = 2");
			} else if(statusId == 3) {
				sb.append(" WHERE d.id = 3");
			} else if(statusId == 4) {
				sb.append(" WHERE d.id = 4");
			}
			sb.append(" ORDER BY b.created_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderBundle ob = new OrderBundle();
				
				// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 주문상태, 송장번호, 합계
				ob.setOrderBundleId(rs.getLong("orderbundleid"));
				ob.setCreatedDate(rs.getString("created_date"));
				ob.setUserEmail(rs.getString("email"));
				ob.setTel(rs.getString("tel"));
				ob.setReceiveName(rs.getString("receive_name"));
				ob.setStatusName(rs.getString("status_name"));
				ob.setDeliveryId(rs.getLong("delivery_id"));
				ob.setTotalPrice(rs.getLong("tot"));
				
				orderBundleList.add(ob);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return orderBundleList;
	}
	
	
	// 전체 주문내역 확인 >> orderBundle 데이터만 출력 (OrderBundle 내 OrderItem List 출력X) - 검색
	@Override
	public List<OrderBundle> findOrderAll(int offset, int size, String condition, String keyword, int statusId) {
		List<OrderBundle> orderBundleList = new ArrayList<OrderBundle>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 주문상태, 송장번호, 합계			
			sb.append("SELECT DISTINCT b.id as orderbundleid, b.created_date, a.email, b.tel, ");
			sb.append(" b.receive_name, d.status_name, b.delivery_id, NVL(tot,0) tot ");
			sb.append(" FROM member a ");
			sb.append(" JOIN order_bundle b ON a.id = b.member_id ");
			sb.append(" JOIN order_item c ON b.id = c.order_id ");
			sb.append(" JOIN order_status d ON c.status_id = d.id ");
			sb.append(" LEFT OUTER JOIN( ");
			sb.append("       SELECT order_id, sum(final_price) tot FROM order_item GROUP BY order_id ");
			sb.append(" ) s ON c.order_id = s.order_id ");
			
			// status 상태조건, condition 검색조건
			switch(statusId) {
			case 1:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 1 AND a.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 1 AND b.id = ?");
				}
				break;
			case 2:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 2 AND a.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 2 AND b.id = ?");
				}
				break;
			case 3:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 3 AND a.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 3 AND b.id = ?");
				}
				break;
			case 4:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 4 AND a.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 4 AND b.id = ?");
				}
				break;
			}
			sb.append(" ORDER BY b.created_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(statusId >=1 && statusId <=4) {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			} else {
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);
			}
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderBundle ob = new OrderBundle();
				
				// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 주문상태, 송장번호, 합계
				ob.setOrderBundleId(rs.getLong("orderbundleid"));
				ob.setCreatedDate(rs.getString("created_date"));
				ob.setUserEmail(rs.getString("email"));
				ob.setTel(rs.getString("tel"));
				ob.setReceiveName(rs.getString("receive_name"));
				ob.setStatusName(rs.getString("status_name"));
				ob.setDeliveryId(rs.getLong("delivery_id"));
				ob.setTotalPrice(rs.getLong("tot"));
				
				orderBundleList.add(ob);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return orderBundleList;
	}
	
	// 상세 주문내역 확인 >> orderBundle, orderitem 데이터 출력 (OrderBundle 내 OrderItem List 출력O)	
	@Override
	public OrderBundle findOrderDetail(int offset, int size, int statusId, int orderBundleId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		OrderBundle orderBundle = new OrderBundle();
		List<OrderItem> orderItems = new ArrayList<>();
		
		try {
			// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 우편번호, 주소1, 주소2, 주문상태, 송장번호, 합계
			sb.append("SELECT DISTINCT b.id as orderbundleid, b.created_date, a.email, b.tel, ");
			sb.append(" b.receive_name, b.post_num, b.address1, b.address2, ");
			sb.append(" d.status_name, b.delivery_id, NVL(tot,0) tot ");
			sb.append(" FROM member a ");
			sb.append(" JOIN order_bundle b ON a.id = b.member_id ");
			sb.append(" JOIN order_item c ON b.id = c.order_id ");
			sb.append(" JOIN order_status d ON c.status_id = d.id ");
			sb.append(" LEFT OUTER JOIN( ");
			sb.append("       SELECT order_id, sum(final_price) tot FROM order_item  GROUP BY order_id ");
			sb.append(" ) s ON c.order_id = s.order_id ");
			
			// status주문상태 검색 조건
			if(statusId == 1) {
				sb.append(" WHERE d.id = 1");
			} else if(statusId == 2) {
				sb.append(" WHERE d.id = 2");
			} else if(statusId == 3) {
				sb.append(" WHERE d.id = 3");
			} else if(statusId == 4) {
				sb.append(" WHERE d.id = 4");
			}
			sb.append(" ORDER BY b.created_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				OrderBundle ob = new OrderBundle();

				// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 주문상태, 송장번호, 합계
				ob.setOrderBundleId(rs.getLong("orderbundleid"));
				ob.setCreatedDate(rs.getString("created_date"));
				ob.setUserEmail(rs.getString("email"));
				ob.setTel(rs.getString("tel"));
				ob.setReceiveName(rs.getString("receive_name"));
				ob.setPostNum(rs.getString("post_num"));
				ob.setAddress1(rs.getString("address1"));
				ob.setAddress2(rs.getString("address2"));
				ob.setStatusName(rs.getString("status_name"));
				ob.setDeliveryId(rs.getLong("delivery_id"));
				ob.setTotalPrice(rs.getLong("tot"));
			} 
			
			pstmt.close();
			pstmt = null;
			rs.close();
			rs = null;
			
			// 입력받은 orderBundleId에 대한 orderItems 조회 쿼리
			// 주문번호, 주문상세번호, 상품명, 단가, 수량, 상품별합계, 주문상태명, 우편번호, 주소1, 주소2
			// 주문상세번호, 상품번호, 주문번호, 주문상태번호, 수량, 단가, 상품별합계, 상품명, 주문상태명
			sb.append("SELECT c.id orderitemid, c.product_id, b.id orderbundleid, c.status_id, c.quantity, ");
			sb.append(" c.price, c.final_price, d.name, e.status_name ");
			sb.append(" FROM member a  ");
			sb.append(" INNER JOIN order_bundle b ON a.id = b.member_id");
			sb.append(" INNER JOIN order_item c ON b.id = c.order_id");
			sb.append(" INNER JOIN product d ON d.id = c.product_id");
			sb.append(" INNER JOIN order_status e ON c.status_id = e.id");
			// status주문상태 검색 조건
			if(statusId == 1) {
				sb.append(" WHERE e.id = 1");
			} else if(statusId == 2) {
				sb.append(" WHERE e.id = 2");
			} else if(statusId == 3) {
				sb.append(" WHERE e.id = 3");
			} else if(statusId == 4) {
				sb.append(" WHERE e.id = 4");
			}
			sb.append(" ORDER BY b.created_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			// orderItems 조회
			while (rs.next()) {
				OrderItem orderItem = new OrderItem();
				
				// 주문상세번호, 상품번호, 주문번호, 주문상태번호, 수량, 단가, 상품별합계, 상품명, 주문상태명
				orderItem.setOrderBundleId(rs.getLong("orderitemid"));
				orderItem.setProductId(rs.getLong("product_id"));
				orderItem.setOrderBundleId(rs.getLong("orderbundleid"));
				orderItem.setStatusId(rs.getLong("status_id"));
				orderItem.setQuantity(rs.getInt("quantity"));
				orderItem.setPrice(rs.getLong("price"));
				orderItem.setFinalPrice(rs.getLong("final_price"));
				orderItem.setProductName(rs.getString("name"));
				orderItem.setStatusName(rs.getString("status_name"));

				orderItems.add(orderItem);
			} 
			
			orderBundle.setOrderItems(orderItems);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return orderBundle;
	}
	
	// 상세 주문내역 확인 >> orderBundle, orderitem 데이터 출력 (OrderBundle 내 OrderItem List 출력O)	 - 검색
	@Override
	public OrderBundle findOrderDetail(int offset, int size, String condition, String keyword, int statusId, int orderBundleId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		OrderBundle orderBundle = new OrderBundle();
		List<OrderItem> orderItems = new ArrayList<>();
		
		try {
			// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 우편번호, 주소1, 주소2, 주문상태, 송장번호, 합계
			sb.append("SELECT DISTINCT b.id as orderbundleid, b.created_date, a.email useremail, b.tel, ");
			sb.append(" b.receive_name, b.post_num, b.address1, b.address2, ");
			sb.append(" d.status_name, b.delivery_id, NVL(tot,0) tot ");
			sb.append(" FROM member a ");
			sb.append(" JOIN order_bundle b ON a.id = b.member_id ");
			sb.append(" JOIN order_item c ON b.id = c.order_id ");
			sb.append(" JOIN order_status d ON c.status_id = d.id ");
			sb.append(" LEFT OUTER JOIN( ");
			sb.append("       SELECT order_id, sum(final_price) tot FROM order_item  GROUP BY order_id ");
			sb.append(" ) s ON c.order_id = s.order_id ");
			// status 상태조건, condition 검색조건
			switch(statusId) {
			case 1:
				if(condition.equals("useremail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 1 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 1 AND b.id = ?");
				}
			case 2:
				if(condition.equals("useremail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 2 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 2 AND b.id = ?");
				}
			case 3:
				if(condition.equals("useremail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 3 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 3 AND b.id = ?");
				}
			case 4:
				if(condition.equals("a.email")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 4 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 4 AND b.id = ?");
				}
			}
			sb.append(" ORDER BY b.created_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			if(statusId >=1 && statusId <=4) {
				pstmt.setString(1, keyword);				
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			} else {
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				OrderBundle ob = new OrderBundle();

				// 주문번호, 주문일, 주문자이메일, 전화번호, 받는분, 주문상태, 송장번호, 합계
				ob.setOrderBundleId(rs.getLong("orderbundleid"));
				ob.setCreatedDate(rs.getString("created_date"));
				ob.setUserEmail(rs.getString("email"));
				ob.setTel(rs.getString("tel"));
				ob.setMemberId(rs.getLong("memberid"));
				ob.setDeliveryId(rs.getLong("delivery_id"));
				ob.setReceiveName(rs.getString("receive_name"));
				ob.setPostNum(rs.getString("post_num"));
				ob.setAddress1(rs.getString("address1"));
				ob.setAddress2(rs.getString("address2"));
				ob.setStatusName(rs.getString("status_name"));
				ob.setDeliveryId(rs.getLong("delivery_id"));
				ob.setTotalPrice(rs.getLong("tot"));
			} 
			
			pstmt.close();
			pstmt = null;
			rs.close();
			rs = null;
			
			// 입력받은 orderBundleId에 대한 orderItems 조회 쿼리
			// 주문상세번호, 상품번호, 주문번호, 주문상태번호, 수량, 단가, 상품별합계, 상품명, 주문상태명
			sb.append("SELECT c.id orderitemid, c.product_id, b.id orderbundleid, c.status_id, c.quantity, ");
			sb.append(" c.price, c.final_price, d.name, e.status_name ");
			sb.append(" FROM member a  ");
			sb.append(" INNER JOIN order_bundle b ON a.id = b.member_id");
			sb.append(" INNER JOIN order_item c ON b.id = c.order_id");
			sb.append(" INNER JOIN product d ON d.id = c.product_id");
			sb.append(" INNER JOIN order_status e ON c.status_id = e.id");
			// status 상태조건, condition 검색조건
			switch(statusId) {
			case 1:
				if(condition.equals("useremail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 1 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 1 AND b.id = ?");
				}
			case 2:
				if(condition.equals("useremail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 2 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 2 AND b.id = ?");
				}
			case 3:
				if(condition.equals("useremail")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 3 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 3 AND b.id = ?");
				}
			case 4:
				if(condition.equals("a.email")) {
					// 주문자이메일 검색
					sb.append(" WHERE d.id = 4 AND a.email = ?");
				} else if(condition.equals("orderbundleid")) {
					// 주문번호 검색
					sb.append(" WHERE d.id = 4 AND b.id = ?");
				}
			}
			sb.append(" ORDER BY b.created_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(statusId >=1 && statusId <=4) {
				pstmt.setString(1, keyword);				
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			} else {
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);
			}
			
			rs = pstmt.executeQuery();
			
			// orderItems 조회
			while (rs.next()) {
				OrderItem orderItem = new OrderItem();
				
				// 주문상세번호, 상품번호, 주문번호, 주문상태번호, 수량, 단가, 상품별합계, 상품명, 주문상태명
				orderItem.setOrderBundleId(rs.getLong("orderitemid"));
				orderItem.setProductId(rs.getLong("product_id"));
				orderItem.setOrderBundleId(rs.getLong("orderbundleid"));
				orderItem.setStatusId(rs.getLong("status_id"));
				orderItem.setQuantity(rs.getInt("quantity"));
				orderItem.setPrice(rs.getLong("price"));
				orderItem.setFinalPrice(rs.getLong("final_price"));
				orderItem.setProductName(rs.getString("name"));
				orderItem.setStatusName(rs.getString("status_name"));

				orderItems.add(orderItem);
			} 
			orderBundle.setOrderItems(orderItems);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return orderBundle;
	}
	
	// 데이터 개수
	@Override
	public int dataCount(int statusId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT NVL(COUNT(*), 0) FROM order_bundle ob");
			sb.append(" JOIN order_item oi ON ob.id = oi.order_id");
			
			if(statusId == 1) {
				sb.append(" WHERE oi.status_id = 1");
			} else if(statusId == 2) {
				sb.append(" WHERE oi.status_id = 2");
			} else if(statusId == 3) {
				sb.append(" WHERE oi.status_id = 3");
			} else if(statusId == 4) {
				sb.append(" WHERE oi.status_id = 4");
			}
			
			pstmt = conn.prepareStatement(sb.toString());

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
	
	// 검색할 때 데이터 개수
	@Override
	public int dataCount(String condition, String keyword, int statusId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT NVL(COUNT(*), 0) FROM order_item oi ");
			sb.append(" JOIN order_bundle ob ON oi.order_id = ob.id ");
			sb.append(" JOIN member m ON ob.member_id = m.id ");
			sb.append(" JOIN order_status os ON os.id = oi.status_id ");

			switch(statusId) {
			case 1:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE oi.status_id =1 AND m.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE oi.status_id = 1 AND ob.id = ?");
				}
				break;
			case 2:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE oi.status_id = 2 AND m.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE oi.status_id = 2 AND ob.id = ?");
				}
				break;
			case 3:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE oi.status_id = 3 AND m.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE oi.status_id = 3 AND ob.id = ?");
				}
				break;
			case 4:
				if(condition.equals("userEmail")) {
					// 주문자이메일 검색
					sb.append(" WHERE oi.status_id = 4 AND m.email = ?");
				} else if(condition.equals("orderBundleId")) {
					// 주문번호 검색
					sb.append(" WHERE oi.status_id = 4 AND ob.id = ?");
				}
				break;
			}

			pstmt = conn.prepareStatement(sb.toString());
			
			if(statusId >=1 && statusId <=4) {
				pstmt.setString(1, keyword);
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

	// 기간 조회(1일 | 1개월 | 6개월 | 1년 | 전체) + 상품별 매출 통계
	@Override
	public List<OrderStatistics> salesStatisticsByProduct(int mode)  {
		List<OrderStatistics> osList = new ArrayList<OrderStatistics>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
      
		try {
			// 
			sb.append("SELECT b.product_id, a.name, sum(b.quantity) as 판매수량, ");
			sb.append(" sum(b.final_price) as 최종매출액 ");
			sb.append(" FROM order_item b ");
			sb.append(" JOIN product a ON a.id = b.product_id ");
			sb.append(" JOIN order_bundle c ON c.id = b.order_id ");
			sb.append(" WHERE b.status_id <= 3");
			
			// 기간 조건(1일 | 1개월 | 6개월 | 1년 | 전체)
			if (mode == 1) { 
				// 1일
				sb.append(" AND c.created_date >= TO_CHAR(SYSDATE -1)");
			} else if (mode == 2) { 
				// 1개월
				sb.append(" AND c.created_date >= ADD_MONTHS(SYSDATE, -1)");
			} else if(mode == 3) {
				// 6개월
				sb.append(" AND c.created_date >= ADD_MONTHS(SYSDATE, -6)");
			} else if(mode == 4) {
				// 1년
				sb.append(" AND c.created_date >= ADD_MONTHS(SYSDATE, -12)");
			} else {
				// 전체
			}
			sb.append(" GROUP BY b.product_id, a.name");
			sb.append(" ORDER BY b.product_id ");

			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderStatistics os = new OrderStatistics();
				os.setProductid(rs.getLong(1));
				os.setProductname(rs.getString(2));
				os.setSumquantity(rs.getLong(3));
				os.setSumfinal_price(rs.getLong(4));
				
				osList.add(os);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt,rs);
		}
		return osList;
	}
	
}
