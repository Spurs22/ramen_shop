package com.repository.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.Product;
import com.DTO.ProductBoard;
import com.DTO.ProductCategory;
import com.util.DBConn;
import com.util.DBUtil;

public class MypageProductRepositoryImpl implements MypageProductRepository {
	private Connection conn = DBConn.getConnection();
	
	
	@Override
	public List<ProductBoard> findLikePostLikeById(Long memberId, int offset, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<ProductBoard> result = new ArrayList<>();

		try {
			sql = "SELECT pb.id, member_id, REMAIN_QUANTITY, NAME, PRICE, content, created_date, hit_count, ROUND(TRUNC((NVL(rating, 0)), 1)*2)/2 as rating, category_id " 
					 +	"  FROM PRODUCT_BOARD pb " 
					 +  "  JOIN product p ON pb.id = p.id " 
					 +  "  LEFT JOIN ( "
					 +  "  SELECT oi.product_id, AVG(rating) as rating " 
					 +  "  FROM product_comment pc " 
					 +  "  JOIN order_item oi on oi.id = order_item_id " 
					 +  "  GROUP BY oi.product_id) " 
					 +  "  cm ON p.id = cm.product_id " 
					 +  "  WHERE pb.id in ("
					 +  "  SELECT product_board_id " 
					 +  "  FROM product_like " 
					 +  "  WHERE member_id = ?) " 
					 +  "  ORDER BY ID DESC " 
					 +  "  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(getProductBoard(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public int dataCount(Long memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM product_board WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}

	@Override
	public int likedataCount(Long memberId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM product_like WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return result;
	}
	
	public ProductBoard getProductBoard(ResultSet rs) throws SQLException {
		return new ProductBoard(
				new Product(
						rs.getLong("id"),
						ProductCategory.getByValue(rs.getInt("category_id")),
						rs.getString("name"),
						rs.getInt("remain_quantity"),
						null
				),
				rs.getLong("member_id" ),
				null,
				rs.getString("content" ),
				rs.getString("created_date" ),
				rs.getInt("hit_count" ),
				rs.getFloat("rating" ),
				rs.getInt("price")
		);
	}

}
