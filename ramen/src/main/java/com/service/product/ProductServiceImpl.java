package com.service.product;


import com.DTO.Product;
import com.repository.product.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {

	ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void createProduct(Product product) {

	}

	@Override
	public void editProduct(Long productId, Product product) {

	}

	@Override
	public void deleteProduct(Product product) {

	}

	@Override
	public List<Product> findAllProduct() {
		return productRepository.findAllProduct();
	}

	@Override
	public Integer getProductQuantity(Long productId) {
		return productRepository.getProductQuantity(productId);
	}

	@Override
	public Product findProductByProductId(Long productId) {
		return productRepository.findProductByProductId(productId);
	}

	@Override
	public List<Product> findProductByName(String name) {
		return null;
	}

	@Override
	public List<Product> findNotRegisteredProduct() {
		return productRepository.findNotRegisteredProduct();
	}

	@Override
	public void editQuantity(Long productId, Integer amount) {

	}
}
