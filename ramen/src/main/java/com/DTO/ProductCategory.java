package com.DTO;

public enum ProductCategory {

		BONGJI(1, "봉지 라면"),
		CUP(2, "컵 라면"),
		BIBIM(3, "비빔 라면"),
		TOPPING(4, "토핑");

		private int value;
		private String label;

		private ProductCategory(int value, String label) {
			this.value = value;
			this.label = label;
		}

		public int getValue() {
			return value;
		}

		public String getLabel() {
			return label;
		}

	public static ProductCategory getByValue(int value) {
		for (ProductCategory type : ProductCategory.values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		throw new IllegalArgumentException("No enum constant with value: " + value);
	}

}
