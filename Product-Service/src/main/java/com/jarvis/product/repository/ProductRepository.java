package com.jarvis.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jarvis.product.entity.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {

	Optional<Products> findByNameAndDeletedFalse(String name);

	Page<Products> findAllByDeletedFalse(Pageable pageable);

	Page<Products> findByCategoryAndDeletedFalse(String category, Pageable pageable);

	Page<Products> findByPriceBetweenAndDeletedFalse(Double minPrice, Double maxPrice, Pageable pageable);

	Page<Products> findByQuantityBetweenAndDeletedFalse(Integer minQty, Integer maxQty, Pageable pageable);

}
