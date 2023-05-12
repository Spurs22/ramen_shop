package com.test;

import com.DTO.Product;
import com.repository.product.ProductRepository;
import com.repository.product.ProductRepositoryImpl;

public class ProductRepositoryImplTest {
	public static void main(String[] args) {
		ProductRepository productRepository = new ProductRepositoryImpl();
		Product product = new Product(
				null,
				1,
				"짜파게티",
				1600,
				12,
				null
		);

		productRepository.createProduct(product);

	}
}
