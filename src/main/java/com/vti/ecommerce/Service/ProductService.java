package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> getAllProduct();
    public ResponseEntity<ProductDTO> getProductById(Long productId);
    public Product addProduct(Product product);
    public Product updateProduct(Product product, Long productId);
    public Product deleteProduct(Long productId);
    public Product activeProduct(Long productId);
    public Product searchProduct();
    public List<ProductDTO> searchProductByCategory(Long categoryId);

}
