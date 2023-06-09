package com.repository.recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DTO.RecipeBoard;
import com.DTO.RecipeProduct;
import com.util.DBConn;
import com.util.DBUtil;

public class RecipeBoardRepositoryImpl implements RecipeBoardRepository {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void insertRecipe(RecipeBoard recipeboard) throws SQLException {
		// 등록해야 하는 것 : 아이디, 멤버 아이디(id), 생성일, 제목, 내용(상품, 수량), 조회 수, 아이피 주소 
		// 레시피 프로덕트에 상품 수량
		PreparedStatement pstmt = null;
		String sql;
		List<RecipeProduct> list = recipeboard.getRecipeProduct();
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO recipe_board (id, member_id, created_date, subject, content, hit_count, ip_address) "
					+ " VALUES (recipe_board_seq.NEXTVAL, ?, SYSDATE, ?, ?, 0, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeboard.getMemberId());
			pstmt.setString(2, recipeboard.getSubject());
			pstmt.setString(3, recipeboard.getContent());
			pstmt.setString(4, recipeboard.getIpAddress());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "INSERT INTO recipe_product (recipe_id, product_id, quantity) "
					+ " VALUES (recipe_board_seq.CURRVAL, ?, ?)";
			
			for(RecipeProduct recipe : list) {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, recipe.getProductId());
				pstmt.setInt(2, recipe.getQuantity());
				
				pstmt.executeUpdate();
				pstmt.close();
				pstmt = null;
			}
			
			sql = "INSERT INTO recipe_picture (id, recipe_id, picture_path) VALUES (recipe_picture_seq.NEXTVAL, recipe_board_seq.CURRVAL, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, recipeboard.getPicture());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
	}

	@Override
	public void updateRecipe(RecipeBoard recipeBoard) throws SQLException {
		// 레시피 수정
		PreparedStatement pstmt = null;
		String sql;
		List<RecipeProduct> list = recipeBoard.getRecipeProduct();
		
		try {
			conn.setAutoCommit(false);
			
			sql = "UPDATE recipe_board SET subject = ?, content = ?, ip_address = ? WHERE id = ? AND member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, recipeBoard.getSubject());
			pstmt.setString(2, recipeBoard.getContent());
			pstmt.setString(3, recipeBoard.getIpAddress());
			pstmt.setLong(4, recipeBoard.getRecipeId());
			pstmt.setLong(5, recipeBoard.getMemberId());
			
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			sql = "DELETE FROM recipe_product WHERE recipe_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeBoard.getRecipeId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "INSERT INTO recipe_product (recipe_id, product_id, quantity) "
					+ " VALUES (?, ?, ?)";
				
			for(RecipeProduct recipe : list) {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, recipeBoard.getRecipeId());
				pstmt.setLong(2, recipe.getProductId());
				pstmt.setInt(3, recipe.getQuantity());
				
				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;
			}
			
			sql = "DELETE FROM recipe_picture WHERE recipe_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeBoard.getRecipeId());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "INSERT INTO recipe_picture (id, recipe_id, picture_path) VALUES (recipe_picture_seq.NEXTVAL, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeBoard.getRecipeId());
			pstmt.setString(2, recipeBoard.getPicture());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		
	}

	@Override
	public void deleteRecipe(Long memberId, Long postId) throws SQLException {
		// 삭제
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			conn.setAutoCommit(false);
			
			sql = "DELETE FROM recipe_product WHERE recipe_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "DELETE FROM recipe_picture WHERE recipe_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "DELETE FROM recipe_like WHERE recipe_id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "DELETE FROM recipe_comment WHERE board_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "DELETE FROM recipe_board WHERE id = ? AND member_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, postId);
			pstmt.setLong(2, memberId);
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
			}
		}
		
	}

	@Override
	public List<RecipeBoard> readRecipe() {
		// 리스트
		// id, nickname, userId, subject content, hitCount, created_date
		
		List<RecipeBoard> list = new ArrayList<RecipeBoard>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard recipe = null;
		
		try {
			sql = "SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, NVL(recipeLikeCount, 0) recipeLikeCount, p.picture_path "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN recipe_picture p ON p.recipe_id = r.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id "
					+ " ORDER BY id DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				recipe.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				recipe.setPicture(rs.getString("picture_path"));
				
				list.add(recipe);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipe(String condition, String keyword) {
		List<RecipeBoard> list = new ArrayList<RecipeBoard>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id ";
			if(condition.equals("all")) {
				sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (condition.equals("created_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ?";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1";
			}
			sql += " ORDER BY id DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			} else {
				pstmt.setString(1, keyword);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RecipeBoard recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				
				list.add(recipe);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public int dataCount() {
		// 페이징 안하면 필요없음
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM recipe_board";
			pstmt = conn.prepareStatement(sql);
			
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
	public int dataCount(String condition, String keyword) {
		// 페이징 안하면 필요없음
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM recipe_board ";
			if(condition.equals("all")) {
				sql += " WHERE INSTR(subject, ?) >= 1 or INSTR(content, ?) >= 1 ";
			} else if (condition.equals("created_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ?";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if(condition.equals("all")) {
				pstmt.setString(2, keyword);
			}
			
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
	public RecipeBoard readRecipe(Long id) {
		// 글 정보 불러오기
		// recipe_board : subject, content, hit_count, created_date 
		// member : user_id, nickname
		// recipe_product : list(product_id, quantity)
		RecipeBoard recipeBoard = null;
		RecipeProduct recipeProduct = null;
		List<RecipeProduct> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT r.id, r.member_id, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, nickname, NVL(recipeLikeCount, 0) recipeLikeCount, p.picture_path "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN recipe_picture p ON r.id = p.recipe_id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id "
					+ " WHERE r.id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recipeBoard = new RecipeBoard();
				
				recipeBoard.setId(rs.getLong("id"));
				recipeBoard.setMemberId(rs.getLong("member_id"));
				recipeBoard.setSubject(rs.getString("subject"));
				recipeBoard.setContent(rs.getString("content"));
				recipeBoard.setHitCount(rs.getInt("hit_count"));
				recipeBoard.setCreatedDate(rs.getString("created_date"));
				recipeBoard.setNickname(rs.getString("nickname"));
				recipeBoard.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				recipeBoard.setPicture(rs.getString("picture_path"));
			}
			
			pstmt.close();
			rs.close();
			pstmt = null;
			rs = null;
			
			sql = "SELECT product_id, quantity, name, picture, category_id "
					+ " FROM recipe_product "
					+ " JOIN product ON recipe_product.product_id = product.id "
					+ " WHERE recipe_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipeProduct = new RecipeProduct();
				
				recipeProduct.setProductId(rs.getLong("product_id"));
				recipeProduct.setName(rs.getString("name"));
				recipeProduct.setQuantity(rs.getInt("quantity"));
				recipeProduct.setPicture(rs.getString("picture"));
				recipeProduct.setCategory(rs.getInt("category_id"));
				
				list.add(recipeProduct);
			}
			
			recipeBoard.setRecipeProduct(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return recipeBoard;
	}

	@Override
	public RecipeBoard preReadRecipe(Long id, String condition, String keyword) {
		RecipeBoard recipeBoard = null;
		RecipeProduct recipeProduct = null;
		List<RecipeProduct> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			if (keyword != null && keyword.length() != 0) {
				sql = " SELECT id, subject "
						+ " FROM recipe_board r "
						+ " JOIN member m ON r.member_id = m.id "
						+ " WHERE ( r.id > ? ) ";
				if(condition.equals("all")) {
					sql += " AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1";
				} else if (condition.equals("created_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sql += " AND ( TO_CHAR(created_date, 'YYYYMMDD') = ? ) ";
				} else {
					sql += " AND ( INSTR(" + condition + ", ?) >= 1 ) ";
				}
				sql += " ORDER BY num ASC ";
				sql += " FETCH FIRST 1 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, id);
				pstmt.setString(2, keyword);
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
	
			} else {
				sql = " SELECT id, subject FROM recipe_board "
						+ "WHERE id > ? "
						+ " ORDER BY id ASC "
						+ " FETCH FIRST 1 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, id);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recipeBoard = new RecipeBoard();
				
				recipeBoard.setId(rs.getLong("id"));
				recipeBoard.setSubject(rs.getString("subject"));
			}
			
			if(recipeBoard == null) {
				return null;
			}
			
			pstmt.close();
			rs.close();
			pstmt = null;
			rs = null;
			
			sql = " SELECT product_id, quantity "
					+ " FROM recipe_product "
					+ " WHERE recipe_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeBoard.getId());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipeProduct = new RecipeProduct();
				
				recipeProduct.setProductId(rs.getLong("product_id"));
				recipeProduct.setQuantity(rs.getInt("quantity"));
				
				list.add(recipeProduct);
			}
			
			recipeBoard.setRecipeProduct(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return recipeBoard;
	}

	@Override
	public RecipeBoard nextReadRecipe(Long id, String condition, String keyword) {
		// TODO Auto-generated method stub
		RecipeBoard recipeBoard = null;
		RecipeProduct recipeProduct = null;
		List<RecipeProduct> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			if (keyword != null && keyword.length() != 0) {
				sql = " SELECT id, subject "
						+ " FROM recipe_board r "
						+ " JOIN member m ON r.member_id = m.id "
						+ " WHERE ( r.id < ? ) ";
				if(condition.equals("all")) {
					sql += " AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1";
				} else if (condition.equals("created_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sql += " AND ( TO_CHAR(created_date, 'YYYYMMDD') = ? ) ";
				} else {
					sql += " AND ( INSTR(" + condition + ", ?) >= 1 ) ";
				}
				sql += " ORDER BY num DESC ";
				sql += " FETCH FIRST 1 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, id);
				pstmt.setString(2, keyword);
				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
	
			} else {
				sql = " SELECT id, subject FROM recipe_board "
						+ "WHERE id < ? "
						+ " ORDER BY id DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, id);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recipeBoard = new RecipeBoard();
				
				recipeBoard.setId(rs.getLong("id"));
				recipeBoard.setSubject(rs.getString("subject"));
			}
			
			if(recipeBoard == null) {
				return null;
			}
			
			pstmt.close();
			rs.close();
			pstmt = null;
			rs = null;
			
			sql = " SELECT product_id, quantity "
					+ " FROM recipe_product "
					+ " WHERE recipe_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, recipeBoard.getId());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipeProduct = new RecipeProduct();
				
				recipeProduct.setProductId(rs.getLong("product_id"));
				recipeProduct.setQuantity(rs.getInt("quantity"));
				
				list.add(recipeProduct);
			}
			
			recipeBoard.setRecipeProduct(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return recipeBoard;
	}

	@Override
	public void registPicture(Long postId, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHitCount(Long id) throws SQLException {
		// TODO 조회 수 증가
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE recipe_board SET hit_count = hit_count + 1 WHERE id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DBUtil.closeResource(pstmt);
		}
		
	}

	@Override
	public List<RecipeBoard> findByMemberId(Long memberId) {
		// 사용자의 글 목록 (제목, 조회 수, 날짜, 글 아이디)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<RecipeBoard> list = new ArrayList<>();
		RecipeBoard board = null;
		
		try {
			sql = "SELECT r.id, subject, hit_count, r.created_date FROM recipe_board r JOIN member m ON m.id = r.member_id WHERE r.member_id = ? ORDER BY r.id DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new RecipeBoard();
				
				board.setId(rs.getLong("id"));
				board.setSubject(rs.getString("subject"));
				board.setHitCount(rs.getInt("hit_count"));
				board.setCreatedDate(rs.getString("created_date"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipeByHitCount() {
		// 조회수 순 정렬
		List<RecipeBoard> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard recipe = null;
		
		try {
			sql = "SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, NVL(recipeLikeCount, 0) recipeLikeCount "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id "
					+ " ORDER BY hit_count DESC, r.id DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				recipe.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				
				list.add(recipe);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipeByHitCount(String condition, String keyword) {
		// 조회수 순 정렬 검색
		List<RecipeBoard> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard recipe = null;
		
		try {
			sql = "SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id ";
			if(condition.equals("all")) {
				sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (condition.equals("created_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ?";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1";
			}
			sql += " ORDER BY hit_count DESC, r.id DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			} else {
				pstmt.setString(1, keyword);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				
				list.add(recipe);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipeByLike() {
		// TODO Auto-generated method stub
		List<RecipeBoard> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard recipe = null;
		
		try {
			sql = "SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, NVL(recipeLikeCount, 0) recipeLikeCount "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id "
					+ " ORDER BY recipeLikeCount DESC, hit_count DESC, r.id DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				recipe.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				
				list.add(recipe);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipeByLike(String condition, String keyword) {
		// TODO Auto-generated method stub
		List<RecipeBoard> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		RecipeBoard recipe = null;
		
		try {
			sql = "SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id ";
			if(condition.equals("all")) {
				sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (condition.equals("created_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ?";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1";
			}
			sql += " ORDER BY hit_count DESC, r.id DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			} else {
				pstmt.setString(1, keyword);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				
				list.add(recipe);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> findByMemberId(Long memberId, int offset, int size) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<RecipeBoard> list = new ArrayList<>();
		RecipeBoard board = null;
		
		try {
			sql = "SELECT r.id, subject, hit_count, r.created_date "
					+ " FROM recipe_board r "
					+ " JOIN member m ON m.id = r.member_id "
					+ " WHERE r.member_id = ? "
					+ " ORDER BY r.id DESC"
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, memberId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new RecipeBoard();
				
				board.setId(rs.getLong("id"));
				board.setSubject(rs.getString("subject"));
				board.setHitCount(rs.getInt("hit_count"));
				board.setCreatedDate(rs.getString("created_date"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipeByAll(String btnradio, String condition, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		List<RecipeBoard> list = new ArrayList<>();

		try {
			sql = " SELECT r.id, m.nickname, subject, content, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, NVL(recipeLikeCount, 0) recipeLikeCount, p.picture_path "
					+ " FROM recipe_board r "
					+ " JOIN member m ON r.member_id = m.id "
					+ " LEFT OUTER JOIN recipe_picture p ON p.recipe_id = r.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id ";
			
			if(keyword != null) {
				if(btnradio.equals("btnradio1")) {
					if(condition.equals("all")) {
						sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 "
								+ " ORDER BY r.id DESC ";
					} else if (condition.equals("created_date")) {
						keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
						sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ? "
								+ " ORDER BY r.id DESC ";
					} else {
						sql += " WHERE INSTR(" + condition + ", ?) >= 1 "
								+ " ORDER BY r.id DESC ";
					}
				} else if (btnradio.equals("btnradio2")) {
					if(condition.equals("all")) {
						sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 "
								+ " ORDER BY hit_count DESC ";
					} else if (condition.equals("created_date")) {
						keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
						sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ? "
								+ " ORDER BY hit_count DESC ";
					} else {
						sql += " WHERE INSTR(" + condition + ", ?) >= 1 "
								+ " ORDER BY hit_count DESC ";
					}
				} else {
					if(condition.equals("all")) {
						sql += " WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 "
								+ " ORDER BY recipeLikeCount DESC ";
					} else if (condition.equals("created_date")) {
						keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
						sql += " WHERE TO_CHAR(r.created_date, 'YYYYMMDD') = ? "
								+ " ORDER BY recipeLikeCount DESC ";
					} else {
						sql += " WHERE INSTR(" + condition + ", ?) >= 1 "
								+ " ORDER BY recipeLikeCount DESC ";
					}
				}
			} else {
				if(btnradio.equals("btnradio1")) {
						sql += " ORDER BY r.id DESC ";
				} else if (btnradio.equals("btnradio2")) {
						sql += " ORDER BY hit_count DESC ";
				} else {
						sql += " ORDER BY recipeLikeCount DESC ";
				}
			}
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword != null) {
				if(btnradio.equals("btnradio1")) {
					if(condition.equals("all")) {
						pstmt.setString(1, keyword);
						pstmt.setString(2, keyword);
					} else {
						pstmt.setString(1, keyword);
					}
				} else if (btnradio.equals("btnradio2")) {
					if(condition.equals("all")) {
						pstmt.setString(1, keyword);
						pstmt.setString(2, keyword);
					} else {
						pstmt.setString(1, keyword);
					}
				} else {
					if(condition.equals("all")) {
						pstmt.setString(1, keyword);
						pstmt.setString(2, keyword);
					} else {
						pstmt.setString(1, keyword);
					}
				}
			} else {
				
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				RecipeBoard recipe = new RecipeBoard();
				
				recipe.setId(rs.getLong("id"));
				recipe.setNickname(rs.getString("nickname"));
				recipe.setSubject(rs.getString("subject"));
				recipe.setContent(rs.getString("content"));
				recipe.setHitCount(rs.getInt("hit_count"));
				recipe.setCreatedDate(rs.getString("created_date"));
				recipe.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				recipe.setPicture(rs.getString("picture_path"));
				
				list.add(recipe);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

	@Override
	public List<RecipeBoard> readRecipeByProduct(Long productId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		List<RecipeBoard> list = new ArrayList<>();
		
		try {
			sql = "SELECT r.id, m.nickname, subject, hit_count, TO_CHAR(r.created_date, 'YYYY-MM-DD') created_date, NVL(recipeLikeCount, 0) recipeLikeCount, rp.picture_path "
					+ " FROM recipe_board r "
					+ " JOIN member m ON m.id = r.member_id "
					+ " JOIN recipe_product p ON p.recipe_id = r.id "
					+ " LEFT OUTER JOIN recipe_picture rp ON rp.recipe_id = r.id "
					+ " LEFT OUTER JOIN ( "
					+ " 	SELECT recipe_id, COUNT(*) recipeLikeCount "
					+ " 	FROM recipe_like "
					+ "		GROUP BY recipe_id "
					+ " ) bc ON bc.recipe_id = r.id "
					+ " WHERE product_id = ? "
					+ " ORDER BY r.id DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, productId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RecipeBoard recipeboard = new RecipeBoard();
				
				recipeboard.setId(rs.getLong("id"));
				recipeboard.setSubject(rs.getString("subject"));
				recipeboard.setHitCount(rs.getInt("hit_count"));
				recipeboard.setCreatedDate(rs.getString("created_date"));
				recipeboard.setRecipeLikeCount(rs.getInt("recipeLikeCount"));
				recipeboard.setPicture(rs.getString("picture_path"));
				
				list.add(recipeboard);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeResource(pstmt, rs);
		}
		
		return list;
	}

}
