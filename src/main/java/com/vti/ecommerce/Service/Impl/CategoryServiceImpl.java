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
    public ResponseEntity<Result> addCategory(Category category) {
        category.setCreated_date(new Date());
        logger.info("SUCCESS");
        return ResponseEntity.ok(new Result("SUCCESS","OK",categoryRepository.save(category)));
    }

    @Override
    public ResponseEntity<Result> updateCategory(Category category, Long categoryId) {
        try {
            Category category1 = categoryRepository.findById(categoryId).orElseThrow(null);
            if(category.getName() != null){
                category1.setName(category.getName());
            }
            if(category.getCategory_image() != null){
                category1.setCategory_image(category.getCategory_image());
            }
            if(category.getDescription() != null){
                category1.setDescription(category.getDescription());
            }
            category1.setUpdate_date(new Date());
            return ResponseEntity.ok(new Result("SUCCESS","OK",categoryRepository.save(category1)));
        }catch (NullPointerException e){
            logger.error("NOT FOUND CATEGORY", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND CATEGORY","NOT_FOUND",null));
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
