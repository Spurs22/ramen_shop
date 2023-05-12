package com.repository.product;


import com.DTO.Product;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public void editProduct(Product product) {

	}

	@Override
	public void deleteProduct(Product product) {

	}

	@Override
	public List<Product> findAllProduct() {
		return null;
	}

	@Override
	public void getProductQuantity(Long productId) {

	}
}
