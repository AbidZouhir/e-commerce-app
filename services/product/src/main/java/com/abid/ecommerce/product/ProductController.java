package com.abid.ecommerce.product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest productRequest
    ){
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
      @RequestBody List<ProductPurchaseRequest> request
    ){
        return ResponseEntity.ok(productService.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findProductById(
            @PathVariable("product-id") Integer productId
    ){
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
}
