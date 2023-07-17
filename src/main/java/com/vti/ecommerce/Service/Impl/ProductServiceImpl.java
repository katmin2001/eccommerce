package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.ProductRequestDTO;
import com.vti.ecommerce.Model.Product;
import com.vti.ecommerce.Model.ProductImage;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Repository.CategoryRepository;
import com.vti.ecommerce.Repository.ProductImageRepository;
import com.vti.ecommerce.Repository.ProductRepository;
import com.vti.ecommerce.Service.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
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
            productDTO.setCreatedDate(product.getCreated_date());
            productDTO.setUpdateDate(product.getUpdate_date());
            productDTO.setCategory(categoryRepository.findById(product.getCategory_id()).orElseThrow());
            productDTO.setProductImages(productImageRepository.findProductsImagesByProductId(product.getId()));
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ResponseEntity<Result> getProductById(Long productId) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(null);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setDescription(product.getDescription());
            productDTO.setCreatedDate(product.getCreated_date());
            productDTO.setUpdateDate(product.getUpdate_date());
            productDTO.setCategory(categoryRepository.findById(product.getCategory_id()).orElseThrow());
            productDTO.setProductImages(productImageRepository.findProductsImagesByProductId(product.getId()));
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",productDTO));
        }catch (NullPointerException e){
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> addProduct(ProductRequestDTO productRequestDTO) {
        if(productRepository.existsProductByName(productRequestDTO.getName())){
            logger.error("EXIST PRODUCT!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("EXIST PRODUCT!","CONFLICT",null));
        }
        else {
            Product product = new Product();
            product.setName(productRequestDTO.getName());
            product.setPrice(productRequestDTO.getPrice());
            product.setDescription(productRequestDTO.getDescription());
            product.setAmount(productRequestDTO.getAmount());
            product.setCreated_date(new Date());
            product.setCategory_id(productRequestDTO.getCategoryId());
            productRepository.save(product);
            List<ProductImage> productImages = new ArrayList<>();
            for(ProductImage productImage: productRequestDTO.getProductImages()){
                ProductImage productImage1 = new ProductImage();
                productImage1.setProduct_id(product.getId());
                productImage1.setSource_image(productImage.getSource_image());
                productImage1.setCreated_date(new Date());
                productImages.add(productImage1);
            }
            productImageRepository.saveAll(productImages);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK",product));
        }
    }

    @Override
    public ResponseEntity<Result> updateProduct(ProductRequestDTO productRequestDTO, Long productId) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(null);
            if(productRequestDTO.getName() != null){
                product.setName(productRequestDTO.getName());
            }
            if(productRequestDTO.getPrice() != null){
                product.setPrice(productRequestDTO.getPrice());
            }
            if(productRequestDTO.getDescription() != null){
                product.setDescription(productRequestDTO.getDescription());
            }
            if(productRequestDTO.getAmount() != null){
                product.setAmount(productRequestDTO.getAmount());
            }
            if(productRequestDTO.getCategoryId() != null){
                product.setCategory_id(productRequestDTO.getCategoryId());
            }
            product.setUpdate_date(new Date());
            productRepository.save(product);
            if(productRequestDTO.getProductImages().size() != 0){
                List<ProductImage> productImages = productImageRepository.findProductsImagesByProductId(productId);
                productImageRepository.deleteAll(productImages);
                for(ProductImage productImage: productRequestDTO.getProductImages()){
                    productImage.setProduct_id(productId);
                    productImage.setCreated_date(new Date());
                    productImage.setUpdate_date(new Date());
                }
                productImageRepository.saveAll(productRequestDTO.getProductImages());
            }
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", product));
        }catch (NullPointerException e){
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> deleteProduct(Long productId) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(null);
            product.setStatus(false);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", product));
        }catch (NullPointerException e){
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> activeProduct(Long productId) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(null);
            product.setStatus(true);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", product));
        }catch (NullPointerException e){
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> searchProduct(String keyword) {
        return ResponseEntity.ok(new Result("SUCCESS","OK", productRepository.findProductsByKeyword(keyword)));
    }

    @Override
    public ResponseEntity<Result> searchProductByCategory(Long categoryId) {
        List<Product> products = productRepository.findProductByCategoryId(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product:products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setDescription(product.getDescription());
            productDTO.setCreatedDate(product.getCreated_date());
            productDTO.setUpdateDate(product.getUpdate_date());
            productDTO.setCategory(categoryRepository.findById(product.getCategory_id()).orElseThrow());
            productDTO.setProductImages(productImageRepository.findProductsImagesByProductId(product.getId()));
            productDTOS.add(productDTO);
        }
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK", productDTOS));
    }

    @Override
    public ResponseEntity<Result> getBestSales() {
        List<Product> products = productRepository.findProductsBySales();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product:products){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setAmount(product.getAmount());
            productDTO.setDescription(product.getDescription());
            productDTO.setCreatedDate(product.getCreated_date());
            productDTO.setUpdateDate(product.getUpdate_date());
            productDTO.setCategory(categoryRepository.findById(product.getCategory_id()).orElseThrow());
            productDTO.setProductImages(productImageRepository.findProductsImagesByProductId(product.getId()));
            productDTOS.add(productDTO);
        }
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK", productDTOS));
    }
}
