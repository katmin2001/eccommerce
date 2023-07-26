package com.vti.ecommerce.Controller;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.ProductRequestDTO;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Service.CategoryService;
import com.vti.ecommerce.Service.OrderService;
import com.vti.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    //quản lý category
    @GetMapping("/category/all")
    public ResponseEntity<Result> getAllCategoryByPage(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "8") int size){
        return categoryService.getCategoryByPage(page, size);
    }
    @GetMapping("/category/detail/{categoryId}")
    public ResponseEntity<Result> getCategoryById(@PathVariable("categoryId") Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }
    @PostMapping("/category/add")
    public ResponseEntity<Result> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @PostMapping("/category/update/{categoryId}")
    public ResponseEntity<Result> updateCategory(@RequestBody Category category,
                                                   @PathVariable("categoryId") Long categoryId){
        return categoryService.updateCategory(category,categoryId);
    }
    @GetMapping("/category/search")
    public ResponseEntity<Result> findCategoriesByKeyword(@RequestParam String q,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8") int size){
        return categoryService.searchCategory(q, page,size);
    }
    @PostMapping("/category/delete/{categoryId}")
    public ResponseEntity<Result> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
    @PostMapping("/category/active/{categoryId}")
    public ResponseEntity<Result> activeCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.activeCategory(categoryId);
    }
    //quản lý product
//    @GetMapping("/product/all")
//    public ResponseEntity<List<ProductDTO>> getAllProduct(){
//        return ResponseEntity.ok(productService.getAllProduct());
//    }
    @GetMapping("/product/all")
    public List<ProductDTO> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "8") int size){
        return productService.getAllProduct(page, size);
    }
    @GetMapping("/product/detail/{productId}")
    public ResponseEntity<Result> getProductById(@PathVariable("productId") Long productId){
        return productService.getProductById(productId);
    }
    @PostMapping("/product/add")
    public ResponseEntity<Result> addProduct(@RequestBody ProductRequestDTO productRequestDTO){
        return productService.addProduct(productRequestDTO);
    }
    @PostMapping("/product/update/{productId}")
    public ResponseEntity<Result> updateProduct(@RequestBody ProductRequestDTO productRequestDTO,
                                                 @PathVariable("productId") Long productId){
        return productService.updateProduct(productRequestDTO, productId);
    }
    @PostMapping("/product/delete/{productId}")
    public ResponseEntity<Result> deleteProduct(@PathVariable("productId") Long productId){
        return productService.deleteProduct(productId);
    }
    @PostMapping("/product/active/{productId}")
    public ResponseEntity<Result> activeProduct(@PathVariable("productId") Long productId){
        return productService.activeProduct(productId);
    }
    @GetMapping("/product/find-by-category/{categoryId}")
    public ResponseEntity<Result> findProductByCategory(@PathVariable("categoryId") Long categoryId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "8") int size){
        return productService.searchProductByCategory(categoryId, page, size);
    }
    @GetMapping("/product/search")
    public ResponseEntity<Result> findProductsByKeyword(@RequestParam String q,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "8") int size){
        return productService.searchProduct(q, page, size);
    }
    //quản lý đơn hàng - order
    @GetMapping("/order/all")
    public ResponseEntity<Result> getAllOrder( @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size){
        return orderService.getAllOrder(page, size);
    }
    @GetMapping("/order/detail/{orderId}")
    public ResponseEntity<Result> getOrderDetail(@PathVariable("orderId") Long orderId){
        return orderService.getOrderDetail(orderId);
    }
}
