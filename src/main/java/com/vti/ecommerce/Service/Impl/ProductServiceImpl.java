package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.Product;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Repository.CategoryRepository;
import com.vti.ecommerce.Repository.ProductImageRepository;
import com.vti.ecommerce.Repository.ProductRepository;
import com.vti.ecommerce.Service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product:products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategoryName(categoryRepository.findById(product.getCategory_id()).orElseThrow().getName());
//            productDTO.setProductImages(productImageRepository.findProductImagesByProduct_id(product.getId()));
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(Long productId) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(null);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategoryName(categoryRepository.findById(product.getCategory_id()).orElseThrow().getName());
//            productDTO.setProductImages(productImageRepository.findProductImagesByProduct_id(product.getId()));
            return ResponseEntity.ok(productDTO);
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        Product product1 = productRepository.findById(productId).orElseThrow();
        if(product.getName() != null){
            product1.setName(product.getName());
        }
        if(product.getPrice() != null){
            product1.setPrice(product.getPrice());
        }
        if(product.getDescription() != null){
            product1.setDescription(product.getDescription());
        }
        if(product.getAmount() != null){
            product1.setAmount(product.getAmount());
        }
        if(product.getCategory_id() != null){
            product1.setCategory_id(product.getCategory_id());
        }
        return productRepository.save(product1);
    }

    @Override
    public Product deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStatus(false);
        return productRepository.save(product);
    }

    @Override
    public Product activeProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStatus(true);
        return productRepository.save(product);
    }

    @Override
    public Product searchProduct() {
        return null;
    }

    @Override
    public List<ProductDTO> searchProductByCategory(Long categoryId) {
        List<Product> products = productRepository.findProductByCategoryId(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product:products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategoryName(categoryRepository.findById(product.getCategory_id()).orElseThrow().getName());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
}
