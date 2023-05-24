package test.repository.product;

import com.DTO.ProductCategory;

public class EnumTest {
	public static void main(String[] args) {
		ProductCategory value = ProductCategory.getByValue(1);

		System.out.println(value.getValue());
		System.out.println(value.getLabel());

	}
}
