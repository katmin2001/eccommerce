package com.vti.ecommerce.Controller;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Service.CategoryService;
import com.vti.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class PublicController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @GetMapping("/home/category-list")
    public ResponseEntity<Result> getAllCategory(){
        return categoryService.getAllCategory();
    }
    @GetMapping("/home/best-sales")
    public ResponseEntity<Result> getBestSales(){
        return productService.getBestSales();
    }
    @GetMapping("/search")
    public ResponseEntity<Result> searchProductsByKeyword(@RequestParam String q){
        return productService.searchProduct(q);
    }
    @GetMapping("/product-list/{categoryId}")
    public ResponseEntity<Result> findProductsByCategory(@PathVariable("categoryId") Long categoryId){
        return productService.searchProductByCategory(categoryId);
    }
    @GetMapping("/product/detail/{productId}")
    public ResponseEntity<Result> getProductDetail(@PathVariable("productId") Long productId){
        return productService.getProductById(productId);
    }
}
