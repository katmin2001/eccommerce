package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.ProductRequestDTO;
import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> getAllProduct();
    public ResponseEntity<Result> getProductById(Long productId);
    public ResponseEntity<Result> addProduct(ProductRequestDTO productRequestDTO);
    public ResponseEntity<Result> updateProduct(ProductRequestDTO productRequestDTO, Long productId);
    public ResponseEntity<Result> deleteProduct(Long productId);
    public ResponseEntity<Result> activeProduct(Long productId);
    public ResponseEntity<Result> searchProduct(String keyword);
    public ResponseEntity<Result> searchProductByCategory(Long categoryId);
    public ResponseEntity<Result> getBestSales();

}
