package com.repository.product;


import com.DTO.Product;

import java.util.List;

public interface ProductRepository {

	void createProduct(Product product);

	void editProduct(Long productId, Product product);

	void deleteProduct(Product product);

	// 등록 여부 까지 반환해야함
	List<Product> findAllProduct();

	Integer getProductQuantity(Long productId);

	Product findProductByProductId(Long productId);

	// 등록 여부 까지 반환해야함
	List<Product> findProductByName(String name);

	List<Product> findNotRegistedProduct();
}
