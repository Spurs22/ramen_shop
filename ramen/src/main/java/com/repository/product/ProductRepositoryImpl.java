package com.repository.product;


import com.DTO.Product;
import com.DTO.ProductCategory;
import com.util.DBConn;
import com.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

	private final Connection conn = DBConn.getConnection();

	@Override
	public void createProduct(Product product) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "insert INTO product (id, category_id, name, remain_quantity, picture) " +
					"VALUES (product_seq.nextval, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product.getCategory().getValue());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getRemainQuantity());
			pstmt.setString(4, product.getPicture());

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
			sql = "UPDATE product SET CATEGORY_ID = ?, NAME = ?, REMAIN_QUANTITY = ?, PICTURE = ? " +
					"WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product.getCategory().getValue());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getRemainQuantity());
			pstmt.setString(4, product.getPicture());
			pstmt.setLong(5, productId);

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
			sql = "SELECT id, category_id, name, remain_quantity, picture FROM PRODUCT ORDER BY ID DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(getProduct(rs));
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
			sql = "SELECT id, category_id, name, remain_quantity, picture FROM PRODUCT WHERE ID = ? ORDER BY ID DESC ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, productId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = getProduct(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	@Override
	public List<Product> findNotRegisteredProduct() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<Product> result = new ArrayList<>();

		try {
			sql = "SELECT p.id, category_id, name, remain_quantity, picture, pc.ID category_id " +
					"FROM PRODUCT p " +
					"LEFT OUTER JOIN product_board pb ON p.id = pb.id " +
					"JOIN product_category pc ON p.category_id = pc.id " +
					"WHERE pb.id is NULL " +
					"ORDER BY p.ID DESC ";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				result.add(getProduct(rs));
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

	@Override
	public void editQuantity(Long productId, Integer amount) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE product SET REMAIN_QUANTITY = ? " +
					"WHERE id = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, amount);
			pstmt.setLong(2, productId);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt);
		}
	}

	@Override
	public boolean isPresentName(String name) {
		boolean result = false;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT count(*) cnt " +
					"FROM product " +
					"WHERE REPLACE(name, ' ', '') = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getInt("cnt") == 1) {
					result = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		return result;
	}

	private static Product getProduct(ResultSet rs) throws SQLException {
		return new Product(
				rs.getLong("id"),
				ProductCategory.getByValue(rs.getInt("category_id")),
				rs.getString("name"),
				rs.getInt("remain_quantity"),
				rs.getString("picture")
		);
	}
}
