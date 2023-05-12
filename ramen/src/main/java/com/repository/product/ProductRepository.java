package com.repository.product;


import com.DTO.Product;

import java.util.List;

public interface ProductRepository {

	void createProduct(Product product);

	void editProduct(Product product);

	void deleteProduct(Product product);

	List<Product> findAllProduct();

	void getProductQuantity(Long productId);

}
