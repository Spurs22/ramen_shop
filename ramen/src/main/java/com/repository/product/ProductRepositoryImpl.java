package com.repository.product;


import com.DTO.Product;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

	private final Connection conn = DBConn.getConnection();

	@Override
	public void createProduct(Product product) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "insert INTO product (id, category_id, name, price, remain_quantity, picture) " +
					"VALUES (product_seq.nextval, ?, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product.getCategory());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getRemainQuantity());
			pstmt.setString(5, product.getPicture());

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void editProduct(Long productId, Product product) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE product SET CATEGORY_ID = ?, NAME = ?, PRICE = ?, REMAIN_QUANTITY = ?, PICTURE = ? " +
					"WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product.getCategory());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getRemainQuantity());
			pstmt.setString(5, product.getPicture());
			pstmt.setLong(6, productId);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public void deleteProduct(Product product) {

	}

	@Override
	public List<Product> findAllProduct() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<Product> result = new ArrayList<>();

		try {
			sql = "SELECT id, category_id, name, price, remain_quantity, picture FROM PRODUCT ORDER BY ID DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(new Product(
						rs.getLong("id"),
						rs.getInt("category_id"),
						rs.getString("name"),
						rs.getInt("price"),
						rs.getInt("remain_quantity"),
						rs.getString("picture")
				));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public Integer getProductQuantity(Long productId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		Integer result = null;

		try {
			sql = "SELECT REMAIN_QUANTITY FROM PRODUCT WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("remain_quantity");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}

		return result;
	}

	@Override
	public Product findProductByProductId(Long productId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		Product result = null;

		try {
			sql = "SELECT id, category_id, name, price, remain_quantity, picture FROM PRODUCT ORDER BY ID DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new Product(
						rs.getLong("id"),
						rs.getInt("category_id"),
						rs.getString("name"),
						rs.getInt("price"),
						rs.getInt("remain_quantity"),
						rs.getString("picture")
				);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public List<Product> findProductByName(String name) {
		return null;
	}
}
