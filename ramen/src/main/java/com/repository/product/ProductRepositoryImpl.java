package main.java.ramen.interfaces.product;

import main.java.ramen.DTO.Product;
import main.java.util.DBConn;
import main.java.util.DBUtil;

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
			sql = "insert INTO product (id, category_id, name, price, remain_quantity) " +
					"VALUES (product_seq.nextval, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);
//			pstmt.setLong(1, productBoard.);

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
