package com.repository.product;


import com.DTO.Product;

import java.util.List;

public interface ProductRepository {

	void createProduct(Product product);

	void editProduct(Long productId, Product product);

	void deleteProduct(Product product);

	List<Product> findAllProduct();

	Integer getProductQuantity(Long productId);

	Product findProductByProductId(Long productId);
}
