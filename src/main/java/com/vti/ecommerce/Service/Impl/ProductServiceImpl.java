package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.ProductRequestDTO;
import com.vti.ecommerce.Model.Product;
import com.vti.ecommerce.Model.ProductImage;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Repository.CategoryRepository;
import com.vti.ecommerce.Repository.ProductImageRepository;
import com.vti.ecommerce.Repository.ProductRepository;
import com.vti.ecommerce.Service.ExcelService;
import com.vti.ecommerce.Service.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final ExcelService excelService;

    public ProductServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository, CategoryRepository categoryRepository, ExcelService excelService) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.excelService = excelService;
    }

    @Override
    public List<ProductDTO> getAllProduct(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageRequest);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
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
        try {
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
            return ResponseEntity.ok(new Result("SUCCESS", "OK", productDTO));
        } catch (NullPointerException e) {
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT", "NOT_FOUND", null));
        }
    }

    @Override
    public ResponseEntity<Result> addProduct(Product product, List<MultipartFile> files) throws IOException {
        if (productRepository.existsProductByName(product.getName())) {
            logger.error("EXIST PRODUCT!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Result("EXIST PRODUCT!", "CONFLICT", null));
        } else {
            product.setCreated_date(new Date());
            productRepository.save(product);

            String uploadDir = "upload/image";
            if (!Files.exists(Paths.get(uploadDir))) {
                Files.createDirectories(Paths.get(uploadDir));
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String[] name = fileName.split("\\.");
                fileName = product.getName() + "-" + product.getId() + "." + name[1];
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath);

                ProductImage productImage = new ProductImage();
                productImage.setSource_image(filePath.toString());
                productImage.setCreated_date(new Date());
                productImage.setProduct_id(product.getId());

                productImages.add(productImage);
            }
            productImageRepository.saveAll(productImages);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS", "OK", product));
        }
    }

    @Override
    public ResponseEntity<Result> updateProduct(Product product, List<MultipartFile> files, Long productId) {
        try {
            Product product1 = productRepository.findById(productId).orElseThrow(null);
            if (product.getName() != null) {
                product1.setName(product.getName());
            }
            if (product.getPrice() != null) {
                product1.setPrice(product.getPrice());
            }
            if (product.getDescription() != null) {
                product1.setDescription(product.getDescription());
            }
            if (product.getAmount() != null) {
                product1.setAmount(product.getAmount());
            }
            if (product.getCategory_id() != null) {
                product1.setCategory_id(product.getCategory_id());
            }
            product1.setUpdate_date(new Date());
            productRepository.save(product1);

            String uploadDir = "upload/image";
            if (files.size() != 0) {
                List<ProductImage> productImageList = productImageRepository.findProductsImagesByProductId(productId);
                for(ProductImage productImage: productImageList){
                    String imagePath = productImage.getSource_image();
                    if(imagePath != null && !imagePath.isEmpty()){
                        Files.deleteIfExists(Paths.get(imagePath));
                    }
                }
                productImageRepository.deleteAll(productImageList);

                List<ProductImage> productImages = new ArrayList<>();
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    String[] name = fileName.split("\\.");
                    fileName = product1.getName() + "-" + productId + "." + name[1];
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.copy(file.getInputStream(), filePath);

                    ProductImage productImage = new ProductImage();
                    productImage.setSource_image(filePath.toString());
                    productImage.setCreated_date(new Date());
                    productImage.setUpdate_date(new Date());
                    productImage.setProduct_id(productId);
                    productImages.add(productImage);

                }
                productImageRepository.saveAll(productImages);
            }
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS", "OK", product1));
        } catch (NullPointerException e) {
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT", "NOT_FOUND", null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result("Error importing product image","BAD_REQUEST", e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<Result> deleteProduct(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(null);
            product.setStatus(false);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS", "OK", product));
        } catch (NullPointerException e) {
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT", "NOT_FOUND", null));
        }
    }

    @Override
    public ResponseEntity<Result> activeProduct(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(null);
            product.setStatus(true);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS", "OK", product));
        } catch (NullPointerException e) {
            logger.error("NOT FOUND PRODUCT", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND PRODUCT", "NOT_FOUND", null));
        }
    }

    @Override
    public ResponseEntity<Result> searchProduct(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productRepository.findProductsByKeyword(keyword, pageRequest);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
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
        return ResponseEntity.ok(new Result("SUCCESS", "OK", productDTOS));
    }

    @Override
    public ResponseEntity<Result> searchProductByCategory(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Product> products = productRepository.findProductByCategoryId(categoryId, pageRequest);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
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
        return ResponseEntity.ok(new Result("SUCCESS", "OK", productDTOS));
    }

    @Override
    public ResponseEntity<Result> getBestSales() {
        List<Product> products = productRepository.findProductsBySales();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
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
        return ResponseEntity.ok(new Result("SUCCESS", "OK", productDTOS));
    }

    @Override
    public ResponseEntity<Result> importProductsFromExcel(MultipartFile file) throws IOException {
        List<List<String>> rows = excelService.readExcel(file);
        List<Product> products = new ArrayList<>();
        int indexNameProduct = 0, indexPrice = 0, indexQuantity = 0, indexCategory = 0, indexDescription = 0;
        for (int i = 0; i < rows.get(0).size(); i++) {
            if (rows.get(0).get(i).equals("Tên sản phẩm")) {
                indexNameProduct = i;
            }
            if (rows.get(0).get(i).equals("Giá")) {
                indexPrice = i;
            }
            if (rows.get(0).get(i).equals("Số lượng hàng")) {
                indexQuantity = i;
            }
            if (rows.get(0).get(i).equals("Loại hàng")) {
                indexCategory = i;
            }
            if (rows.get(0).get(i).equals("Mô tả")) {
                indexDescription = i;
            }
//            switch (rows.get(0).get(i)){
//                case "Tên sản phẩm":
//                     indexNameProduct = i;
//                case "Giá":
//                     indexPrice = i;
//                case "Số lượng hàng":
//                     indexQuantity = i;
//                case "Loại hàng":
//                     indexCategory = i;
//                case "Mô tả":
//                     indexDescription = i;
//            }
        }
        rows.remove(0);
        for (List<String> row : rows) {
            if (row.get(0) == "") {
                break;
            }
            String productName = row.get(indexNameProduct);
            Double price = Double.parseDouble(row.get(indexPrice));
            int quantity = (int) Double.parseDouble(row.get(indexQuantity));
            String categoryName = row.get(indexCategory);
            String description = row.get(indexDescription);

            Product product = new Product();
            product.setName(productName);
            product.setPrice(price);
            product.setAmount(quantity);
            product.setDescription(description);
            product.setCategory_id(categoryRepository.findByName(categoryName).get().getId());
            product.setCreated_date(new Date());

            products.add(product);
            productRepository.save(product);
        }
        return ResponseEntity.ok(new Result("SUCCESS", "OK", products));
    }
}
