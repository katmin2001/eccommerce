package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public ResponseEntity<Result> getAllCategory();
    public ResponseEntity<Result> addCategory(Category category);
    public ResponseEntity<Result> updateCategory(Category category, Long categoryId);
    public ResponseEntity<Result> deleteCategory(Long categoryId);
    public ResponseEntity<Result> getCategoryById(Long categoryId);
    public ResponseEntity<Result> searchCategory(String keyword);
}
