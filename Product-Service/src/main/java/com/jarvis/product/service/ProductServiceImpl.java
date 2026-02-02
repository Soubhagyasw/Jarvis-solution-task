package com.jarvis.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jarvis.product.dto.ProductPatchDTO;
import com.jarvis.product.dto.ProductRequestDTO;
import com.jarvis.product.dto.ProductResponseDTO;
import com.jarvis.product.entity.Products;
import com.jarvis.product.exception.DuplicateResourceException;
import com.jarvis.product.exception.GlobalExceptionHandler;
import com.jarvis.product.exception.ResourceNotFoundException;
import com.jarvis.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final GlobalExceptionHandler globalExceptionHandler;
	
	@Autowired
	private final ProductRepository repository;

	@Override
	public ProductResponseDTO createProduct(ProductRequestDTO dto) {

	    repository.findByNameAndDeletedFalse(dto.getName())
        .ifPresent(p -> {
            throw new DuplicateResourceException("Product already exists with name: " + dto.getName());
        });

		Products product = new Products(

				dto.getName(), dto.getPrice(), dto.getQuantity(), dto.getCategory()

		);

		return mapToDTO(repository.save(product));
	}
	
	 @Override
	    public ProductResponseDTO getProductById( Long id) {
	        Products product = repository.findById(id)
	                .filter(p -> !p.isDeleted())
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	        return mapToDTO(product);
	    }
	 
	 @Override
	    public Page<ProductResponseDTO> getAllProducts(
	            String category,
	            Integer minPrice,
	            Integer maxPrice,
	            Pageable pageable) {

	        return repository.findAllByDeletedFalse(pageable)
	                .map(this::mapToDTO);
	    }
	 
	 @Override
	    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
	                
	        repository.findByNameAndDeletedFalse(dto.getName())
	        .ifPresent(p -> {
	            throw new DuplicateResourceException("Product already exists with name: " + dto.getName());
	        });

	        Products product = getEntity(id);
	        
	        product.setName(dto.getName());
	        product.setPrice(dto.getPrice());
	        product.setQuantity(dto.getQuantity());
	        product.setCategory(dto.getCategory());

	        return mapToDTO(repository.save(product));
	    }

	    @Override
	    public ProductResponseDTO patchProduct(Long id, ProductPatchDTO dto) {
	        Products product = getEntity(id);

	        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
	        if (dto.getQuantity() != null) product.setQuantity(dto.getQuantity());
	        if (dto.getCategory() != null) product.setCategory(dto.getCategory());

	        return mapToDTO(repository.save(product));
	    }

	    @Override
	    public void deleteProduct(Long id) {
	        Products product = getEntity(id);
	        product.setDeleted(true);
	        repository.save(product);
	    }
	    
	    private Products getEntity(Long id) {
	        return repository.findById(id)
	                .filter(p -> !p.isDeleted())
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	    }
	    
	    private ProductResponseDTO mapToDTO(Products product) {
			return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity(),
					product.getCategory());
		}

		@Override
		public List<ProductResponseDTO> getAllData() {
			List<ProductResponseDTO> list = new ArrayList<ProductResponseDTO>();
			List<Products> all = repository.findAll();
			for (Products products : all) {
				ProductResponseDTO mapToDTO = mapToDTO(products);
				list.add(mapToDTO);
			}
			return list;
		}

		@Override
		public Page<Products> getAllProducts(
	            int page,
	            int size,
	            String sortBy,
	            String sortDir,
	            String category,
	            Double minPrice,
	            Double maxPrice,
	            Integer minQty,
	            Integer maxQty) {

	        Sort sort = sortDir.equalsIgnoreCase("desc")
	                ? Sort.by(sortBy).descending()
	                : Sort.by(sortBy).ascending();

	        Pageable pageable = PageRequest.of(page, size, sort);

	        if (category != null) {
	            return repository.findByCategory(category, pageable);
	        }

	        if (minPrice != null && maxPrice != null) {
	            return repository.findByPriceBetween(minPrice, maxPrice, pageable);
	        }

	        if (minQty != null && maxQty != null) {
	            return repository.findByQuantityBetween(minQty, maxQty, pageable);
	        }

	        return repository.findAll(pageable);
	    }


}
