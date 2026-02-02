package com.jarvis.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPatchDTO {

	Integer price;
	Integer quantity;
	String category;

}
