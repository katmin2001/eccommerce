package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.ProductRequestDTO;
import com.vti.ecommerce.Model.Product;
import com.vti.ecommerce.Model.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public ResponseEntity<Result> getAllProduct(int page, int size);
    public ResponseEntity<Result> getProductById(Long productId);
    public ResponseEntity<Result> addProduct(Product product, List<MultipartFile> files) throws IOException;
    public ResponseEntity<Result> updateProduct(Product product, List<MultipartFile> file, Long productId);
    public ResponseEntity<Result> deleteProduct(Long productId);
    public ResponseEntity<Result> activeProduct(Long productId);
    public ResponseEntity<Result> searchProduct(String keyword, int page, int size);
    public ResponseEntity<Result> searchProductByCategory(Long categoryId, int page, int size);
    public ResponseEntity<Result> getBestSales();
    public ResponseEntity<Result> importProductsFromExcel(MultipartFile file) throws IOException;

}
