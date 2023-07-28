package com.vti.ecommerce.Service;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public ResponseEntity<Result> getAllCategory();
    public ResponseEntity<Result> getCategoryByPage(int page, int size);
    public ResponseEntity<Result> addCategory(Category category, MultipartFile file) throws IOException;
    public ResponseEntity<Result> updateCategory(Category category, MultipartFile file, Long categoryId);
    public ResponseEntity<Result> deleteCategory(Long categoryId);
    public ResponseEntity<Result> activeCategory(Long categoryId);
    public ResponseEntity<Result> getCategoryById(Long categoryId);
    public ResponseEntity<Result> searchCategory(String keyword, int page, int size);
}
