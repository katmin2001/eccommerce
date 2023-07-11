package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Repository.CategoryRepository;
import com.vti.ecommerce.Service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        category.setCreated_date(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
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
        return categoryRepository.save(category1);
    }

    @Override
    public Category deleteCategory(Long categoryId) {
        Category category1 = categoryRepository.findById(categoryId).orElseThrow(null);
        category1.setStatus(false);
        return categoryRepository.save(category1);
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
