package com.jarvis.product.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jarvis.product.dto.ProductPatchDTO;
import com.jarvis.product.dto.ProductRequestDTO;
import com.jarvis.product.dto.ProductResponseDTO;
import com.jarvis.product.entity.Products;
import com.jarvis.product.exception.DuplicateResourceException;
import com.jarvis.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:5501")
public class ProductController {

    private final ProductService service;

    @PostMapping("/addproduct")
    public ResponseEntity<ProductResponseDTO> create(
             @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createProduct(dto));
    }

    @GetMapping("findproduct/{id}")
    public ResponseEntity<ProductResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getProductById(id));
    }

//    @GetMapping("/findpage")
//    public ResponseEntity<Page<ProductResponseDTO>> getAll(
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) Integer minPrice,
//            @RequestParam(required = false) Integer maxPrice,
//            Pageable page) {
//
//        return ResponseEntity.ok(
//                service.getAllProducts(category, minPrice, maxPrice, page)
//        );
//    }
    
    @GetMapping("/findpage")
    public Page<Products> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minQty,
            @RequestParam(required = false) Integer maxQty
    ) {
        return service.getAllProducts(
                page, size, sortBy, sortDir,
                category, minPrice, maxPrice,
                minQty, maxQty
        );
    }
    
    @GetMapping("/findall")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        return new ResponseEntity<List<ProductResponseDTO>>(service.getAllData(), HttpStatus.OK);
    }

    @PutMapping("putproduct/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
             @RequestBody ProductRequestDTO dto) {

        return ResponseEntity.ok(service.updateProduct(id, dto));
    }

    @PatchMapping("patchproduct/{id}")
    public ResponseEntity<ProductResponseDTO> patch(
            @PathVariable Long id,
            @RequestBody ProductPatchDTO dto) {

        return ResponseEntity.ok(service.patchProduct(id, dto));
    }

    @DeleteMapping("deleteproduct/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
    	        
        service.deleteProduct(id);
        return new ResponseEntity<String>("Delete Data", HttpStatus.OK) ;
    }
}
