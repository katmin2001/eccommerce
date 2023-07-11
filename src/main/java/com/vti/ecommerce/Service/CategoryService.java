package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<Category> getAllCategory();
    public Category addCategory(Category category);
    public Category updateCategory(Category category, Long categoryId);
    public Category deleteCategory(Long categoryId);
    public Optional<Category> getCategoryById(Long categoryId);
//    public Category searchCategory();
}
