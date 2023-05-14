package com.service.product;


import com.DTO.Product;

import java.util.List;

public interface ProductService {
	void createProduct(Product product);

	void editProduct(Long productId, Product product);

	void deleteProduct(Product product);

	List<Product> findAllProduct();

	Integer getProductQuantity(Long productId);

	Product findProductByProductId(Long productId);

	List<Product> findProductByName(String name);

}
