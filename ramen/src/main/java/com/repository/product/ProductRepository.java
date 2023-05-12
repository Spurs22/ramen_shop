package main.java.ramen.interfaces.product;

import main.java.ramen.DTO.Product;

import java.util.List;

public interface ProductRepository {

	void createProduct(Product product);

	void editProduct(Product product);

	void deleteProduct(Product product);

	List<Product> findAllProduct();

	void getProductQuantity(Long productId);

}
