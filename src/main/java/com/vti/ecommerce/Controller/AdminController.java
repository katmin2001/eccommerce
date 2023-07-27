package com.vti.ecommerce.Controller;

import com.vti.ecommerce.Model.Category;
import com.vti.ecommerce.Model.Coupon;
import com.vti.ecommerce.Model.DTO.CouponDTO;
import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.ProductRequestDTO;
import com.vti.ecommerce.Model.Product;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Service.CategoryService;
import com.vti.ecommerce.Service.CouponService;
import com.vti.ecommerce.Service.OrderService;
import com.vti.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
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
    @Autowired
    private CouponService couponService;
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
    public ResponseEntity<Result> addProduct(@ModelAttribute Product product,
                                             @RequestParam("files") List<MultipartFile> files){
        try {
            return productService.addProduct(product, files);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result("Error importing product image","BAD_REQUEST", e.getMessage()));
        }
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
    @PostMapping("/product/import")
    public ResponseEntity<Result> importProductsByExcel(@RequestParam("file") MultipartFile file){
        try{
            return productService.importProductsFromExcel(file);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result("Error importing products","BAD_REQUEST", e.getMessage()));
        }
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
    //quan ly coupon
    @GetMapping("/coupon/all")
    public ResponseEntity<Result> getAllCoupon(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size){
        return couponService.getAllCoupon(page, size);
    }
    @PostMapping("/coupon/add")
    public ResponseEntity<Result> addCoupon(@RequestBody CouponDTO couponDTO) {
        return couponService.addCoupon(couponDTO);
    }
    @PostMapping("/coupon/update/{couponId}")
    public ResponseEntity<Result> updateCoupon(@RequestBody CouponDTO couponDTO,
                                               @PathVariable("couponId") Long couponId){
        return couponService.updateCoupon(couponDTO,couponId);
    }
    @PostMapping("/coupon/delete/{couponId}")
    public ResponseEntity<Result> deleteCoupon(@PathVariable("couponId") Long couponId){
        return couponService.deleteCoupon(couponId);
    }
    @PostMapping("/coupon/active/{couponId}")
    public ResponseEntity<Result> activeCoupon(@PathVariable("couponId") Long couponId){
        return couponService.activeCoupon(couponId);
    }
}
