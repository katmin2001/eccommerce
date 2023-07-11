package com.vti.ecommerce.Controller;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Service.CategoryService;
import com.vti.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
//    @GetMapping("/get-content")
//    public ResponseEntity<String> getAdminHome(){
//        return ResponseEntity.ok("This content for admin only");
//    }
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    //quản lý category
    @GetMapping("/category/all")
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
    @GetMapping("/category/detail/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    @PostMapping("/category/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.addCategory(category));
    }
    @PostMapping("/category/update/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category,
                                                   @PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(category,categoryId));
    }
    @PostMapping("/category/delete/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
    //quản lý product
    @GetMapping("/product/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }
    @GetMapping("/product/detail/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long productId){
        return productService.getProductById(productId);
    }
//    @PostMapping("/product/add")
//    public ResponseEntity<Product> addProduct(@RequestBody Product product){
//        return ResponseEntity.ok(productService.addProduct(product));
//    }
//    @PostMapping("/product/update/{productId}")
//    public ResponseEntity<Product> updateProduct(@RequestBody Product product,
//                                                 @PathVariable("productId") Long productId){
//        return ResponseEntity.ok(productService.updateProduct(product, productId));
//    }
//    @PostMapping("/product/delete/{productId}")
//    public ResponseEntity<Product> deleteProduct(@PathVariable("productId") Long productId){
//        return ResponseEntity.ok(productService.deleteProduct(productId));
//    }
//    @PostMapping("/product/active/{productId}")
//    public ResponseEntity<Product> activeProduct(@PathVariable("productId") Long productId){
//        return ResponseEntity.ok(productService.activeProduct(productId));
//    }
//    @GetMapping("/product/find-by-category/{categoryId}")
//    public ResponseEntity<List<ProductDTO>> findProductByCategory(@PathVariable("categoryId") Long categoryId){
//        return ResponseEntity.ok(productService.searchProductByCategory(categoryId));
//    }
    //quản lý đơn hàng - order
}
