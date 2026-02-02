package com.jarvis.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jarvis.product.dto.ProductPatchDTO;
import com.jarvis.product.dto.ProductRequestDTO;
import com.jarvis.product.dto.ProductResponseDTO;
import com.jarvis.product.entity.Products;

public interface ProductService {

	ProductResponseDTO createProduct(ProductRequestDTO dto);
	ProductResponseDTO getProductById(Long id);
	
	Page<ProductResponseDTO> getAllProducts(
            String category,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );
	
	ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);

    ProductResponseDTO patchProduct(Long id, ProductPatchDTO dto);

    void deleteProduct(Long id);
    
	List<ProductResponseDTO> getAllData();
	
	Page<Products> getAllProducts(int page, int size, String sortBy, String sortDir, String category, Double minPrice,
			Double maxPrice, Integer minQty, Integer maxQty);

}
