package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Repository.CategoryRepository;
import com.vti.ecommerce.Service.CategoryService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<Result> getAllCategory() {
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK", categoryRepository.findAll()));
    }

    @Override
    public ResponseEntity<Result> getCategoryByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK", categoryRepository.findAll(pageRequest )));
    }

    @Override
    public ResponseEntity<Result> addCategory(Category category, MultipartFile file) throws IOException {
        category.setCreated_date(new Date());
        String uploadDir = "upload/category-images";
        if (!Files.exists(Paths.get(uploadDir))) {
            Files.createDirectories(Paths.get(uploadDir));
        }
        String fileName = file.getOriginalFilename();
        String[] name = fileName.split("\\.");
        fileName = category.getName() + "." + name[1];
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);

        category.setCategory_image(filePath.toString());

        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK",categoryRepository.save(category)));
    }

    @Override
    public ResponseEntity<Result> updateCategory(Category category, MultipartFile file, Long categoryId) {
        try {
            Category category1 = categoryRepository.findById(categoryId).orElseThrow(null);
            if(category.getName() != null){
                category1.setName(category.getName());
            }
            if(category.getDescription() != null){
                category1.setDescription(category.getDescription());
            }
            if(!file.isEmpty()){
                String imagePath = category1.getCategory_image();
                if(imagePath != null && !imagePath.isEmpty()){
                    Files.deleteIfExists(Paths.get(imagePath));
                }

                String uploadDir = "upload/category-images";

                String fileName = file.getOriginalFilename();
                String[] name = fileName.split("\\.");
                fileName = category1.getName() + "." + name[1];
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), filePath);

                category.setCategory_image(filePath.toString());
            }
            category1.setUpdate_date(new Date());
            return ResponseEntity.ok(new Result("SUCCESS","OK",categoryRepository.save(category1)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND CATEGORY", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CATEGORY","NOT_FOUND",null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result("Error importing product image","BAD_REQUEST", e.getMessage()));
        }

    }

    @Override
    public ResponseEntity<Result> deleteCategory(Long categoryId) {
        try{
            Category category1 = categoryRepository.findById(categoryId).orElseThrow(null);
            category1.setStatus(false);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", category1));
        }catch (NullPointerException e){
            logger.error("NOT FOUND CATEGORY", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CATEGORY","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> activeCategory(Long categoryId) {
        try{
            Category category1 = categoryRepository.findById(categoryId).orElseThrow(null);
            category1.setStatus(true);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", category1));
        }catch (NullPointerException e){
            logger.error("NOT FOUND CATEGORY", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CATEGORY","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> getCategoryById(Long categoryId) {
        try{
            Category category1 = categoryRepository.findById(categoryId).orElseThrow(null);
            logger.info("SUCCESS");
            return ResponseEntity.ok(new Result("SUCCESS","OK", category1));
        }catch (NullPointerException e){
            logger.error("NOT FOUND CATEGORY", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CATEGORY","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> searchCategory(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(new Result("SUCCESS","OK", categoryRepository.findCategoriesByKeyword(keyword, pageRequest)));
    }
}
