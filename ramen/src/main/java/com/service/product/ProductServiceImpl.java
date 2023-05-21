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
		productRepository.createProduct(product);
	}

	@Override
	public void editProduct(Long productId, Product product) {
		editProduct(productId, product);
	}

	@Override
	public void deleteProduct(Product product) {
		deleteProduct(product);
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
	public boolean isPresentName(String name) {
		return productRepository.isPresentName(name);
	}

	@Override
	public void subtractQuantity(Long productId, Integer amount) {
		Integer remainQuantity = productRepository.getProductQuantity(productId);
		if (remainQuantity >= amount) {
			productRepository.editQuantity(productId, remainQuantity - amount);
		} else {
			throw new RuntimeException();
		}
	}
}
