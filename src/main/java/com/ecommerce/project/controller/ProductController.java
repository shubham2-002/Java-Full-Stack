package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstant;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody Product product,
                                                 @PathVariable Long categoryId){
      ProductDTO productDTO=  productService.addProduct(product,categoryId);
      return new ResponseEntity<>(productDTO,HttpStatus.CREATED);

    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam (name = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER ,required = false) Integer pageNumber,
                                                          @RequestParam (name = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE ,required = false ) Integer pageSize,
                                                          @RequestParam (name = "sortBy" ,defaultValue = AppConstant.SORT_PRODUCT_BY ,required = false)  String sortBy,
                                                          @RequestParam (name = "sortOrder",defaultValue = AppConstant.SORT_DIRECTION ,required = false)   String sortOrder
                                                          ){
     ProductResponse productResponse=   productService.getAllProduct(pageNumber,pageSize,sortBy,sortOrder);
     return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse>geProductById //getByCateogryId
            (@PathVariable Long categoryId ,
             @RequestParam (name = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER ,required = false) Integer pageNumber,
             @RequestParam (name = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE ,required = false ) Integer pageSize,
             @RequestParam (name = "sortBy" ,defaultValue = AppConstant.SORT_PRODUCT_BY ,required = false)  String sortBy,
             @RequestParam (name = "sortOrder",defaultValue = AppConstant.SORT_DIRECTION ,required = false)   String sortOrder){
        ProductResponse productResponse = productService.searchByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> searchProductByKey
            (@PathVariable String keyword,
             @RequestParam (name = "pageNumber" ,defaultValue = AppConstant.PAGE_NUMBER ,required = false) Integer pageNumber,
             @RequestParam (name = "pageSize" ,defaultValue = AppConstant.PAGE_SIZE ,required = false ) Integer pageSize,
             @RequestParam (name = "sortBy" ,defaultValue = AppConstant.SORT_PRODUCT_BY ,required = false)  String sortBy,
             @RequestParam (name = "sortOrder",defaultValue = AppConstant.SORT_DIRECTION ,required = false)   String sortOrder){
        ProductResponse productResponse = productService.searchProductByKey(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct
            ( @Valid @PathVariable Long productId, @RequestBody ProductDTO productDTO){
        productService.updateProduct(productDTO,productId);
        return  new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public  ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
       ProductDTO deletedProduct= productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct,HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity< ProductDTO> updateProductImage
            (@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedImgProduct= productService.updateProductImg(productId,image);
        return  new ResponseEntity<>(updatedImgProduct,HttpStatus.OK);
    }
}
