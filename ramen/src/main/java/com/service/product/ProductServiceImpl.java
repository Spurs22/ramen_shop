package com.service.product;

import com.DTO.Product;
import com.repository.order.OrderRepository;
import com.repository.product.ProductRepository;
import com.repository.product.ProductRepositoryImpl;

import java.util.List;

public class ProductServiceImpl implements ProductService {

	ProductRepository productRepository;
	OrderRepository orderRepository;

	public ProductServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public void createProduct(Product product) {
		// 만약에 지금 만드려고 하는 이름이 존재하면 합칠거야 개수를
		// 지금 등록된 상품의 개수를 가져왔어
		// 리포지토리에서
//		Integer productQuantity = productRepository.getProductQuantity(product.getId());
//
//		if (productQuantity > 0) {
//
//		} else {
//
//		}
//
//		return result;
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
}
