package com.jarvis.product;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jarvis.product.controller.ProductController;
import com.jarvis.product.dto.ProductPatchDTO;
import com.jarvis.product.dto.ProductRequestDTO;
import com.jarvis.product.dto.ProductResponseDTO;
import com.jarvis.product.exception.GlobalExceptionHandler;
import com.jarvis.product.exception.ResourceNotFoundException;
import com.jarvis.product.service.ProductService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createProduct_success() throws Exception {

        ProductRequestDTO request =
                new ProductRequestDTO("Laptop DELL", 50000, 5, "Electronics");

        ProductResponseDTO response =
                new ProductResponseDTO(1L, "Laptop DELL", 50000, 5, "Electronics");

        Mockito.when(productService.createProduct(any(ProductRequestDTO.class)))
               .thenReturn(response);

        mockMvc.perform(post("/api/products/addproduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop DELL"));
    }

    @Test
    void getProductById_success() throws Exception {

        Mockito.when(productService.getProductById(1L))
                .thenReturn(new ProductResponseDTO(
                        1L, "Phone", 20000, 3, "Electronics"));

        mockMvc.perform(get("/api/products/findproduct/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"));
    }

    @Test
    void getProductById_notFound() throws Exception {

        Mockito.when(productService.getProductById(1L))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/products/findproduct/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProduct_success() throws Exception {

        ProductRequestDTO request =
                new ProductRequestDTO("TV", 30000, 2, "Electronics");

        ProductResponseDTO response =
                new ProductResponseDTO(1L, "TV", 30000, 2, "Electronics");

        Mockito.when(productService.updateProduct(Mockito.eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/products/putproduct/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TV"));
    }

    @Test
    void patchProduct_success() throws Exception {

        ProductPatchDTO patchDTO =
                new ProductPatchDTO(35000, null, null);

        ProductResponseDTO response =
                new ProductResponseDTO(1L, "TV", 35000, 2, "Electronics");

        Mockito.when(productService.patchProduct(Mockito.eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(patch("/api/products/patchproduct/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35000));
    }

    @Test
    void deleteProduct_success() throws Exception {

        Mockito.doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/deleteproduct/1"))
                .andExpect(status().isOk());
    }
}
