// package com.example.demo;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.beans.factory.annotation.Autowired;
// @RestController
// @RequestMapping("/api/products")
// public class ProductInfoController {
//     private final ProductInfoService productService;

//     @Autowired
//     public ProductInfoController(ProductInfoService productService) {
//         this.productService = productService;
//     }

//     /**
//      * @param product
//      * @return
//      */
   

//     // Add other endpoints for retrieving product data
// }
package com.example.demo;
 import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/products")
public class ProductInfoController {

    private final ProductInfoService productService;

    @Autowired
    public ProductInfoController(ProductInfoService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductInfo product) {
        // ProductInfo savedProduct = ;
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }
    // Endpoint to get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductInfo> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }



    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<ProductInfo>> searchProductByName(@PathVariable String searchTerm) {
        List<ProductInfo> searchResults = productService.searchProducts(searchTerm);
        System.out.println("hii i am in search term function by name ");
        if (searchResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchResults);
    }


    @GetMapping("/search/{searchTerm}/{sortOption}")
    public ResponseEntity<List<ProductInfo>> searchProductByName(@PathVariable String searchTerm, @PathVariable String sortOption) {
        List<ProductInfo> searchResults = null;
        if ("name_asc".equals(sortOption)) {
            searchResults = productService.searchProductsSortedByName(searchTerm);
        } else if ("score_des".equals(sortOption)) {
            searchResults = productService.getProductsSortedByScore(searchTerm); // Provide the searchTerm here
        } 
        else if("score_asc".equals(sortOption))
        {
          //  System.out.println("sorting score in service method");
            searchResults = productService.searchAllProductsSortedByScoreAscSearchTerm(searchTerm);
       
        }
       else if("name_des".equals(sortOption))
        {
          //  System.out.println("hiiii");
            searchResults = productService.searchAllProductsSortedByNameDesSearchTerm(searchTerm);
       
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid sort option
        }
    
        if (searchResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchResults);
    }


    @GetMapping("/search/all/{sortOption}")
    public ResponseEntity<List<ProductInfo>> sortAllByName(@PathVariable String sortOption) {
        List<ProductInfo> searchResults= null;
        if("name_asc".equals(sortOption))
        {
            System.out.println("hiiii");
            searchResults = productService.searchAllProductsSortedByName();
       
        }
       else if("score_des".equals(sortOption))
        {
            System.out.println("sorting score in service method");
            searchResults = productService.searchAllProductsSortedByScore();
       
        }
        else if("score_asc".equals(sortOption))
        {
            System.out.println("sorting score in service method");
            searchResults = productService.searchAllProductsSortedByScoreAscending();
       
        }
       else if("name_des".equals(sortOption))
        {
            System.out.println("hiiii");
            searchResults = productService.searchAllProductsSortedByNameDescending();
       
        }
          
        
    
       else if (searchResults == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchResults);
    }
    
    
    // @GetMapping("/api/products/search/{searchTerm}")
    // public ResponseEntity<List<ProductInfo>> searchProduct(@PathVariable String searchTerm, @RequestParam(required = false) String sortBy) {
    //     List<ProductInfo> searchResults;
    //     if (sortBy != null && !sortBy.isEmpty()) {
    //         if (sortBy.equalsIgnoreCase("productName")) {
    //             searchResults = productService.searchProductsSortedByName(searchTerm);
    //         }  else {
    //             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid sortBy parameter
    //         }
    //     } else {
    //         searchResults = productService.searchProducts(searchTerm);
    //     }

    //     if (searchResults.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    //     return ResponseEntity.ok(searchResults);
    // }

    @GetMapping("/search")
    public ResponseEntity<List<ProductInfo>> searchProducts() {
        System.out.println("searching all products");
        List<ProductInfo> searchResults = productService.getAllProducts();
        System.out.println(searchResults.size());
        return ResponseEntity.ok(searchResults);
    }

    // Endpoint to update a product
    @PutMapping("/{id}")
    public ResponseEntity<ProductInfo> updateProduct(@PathVariable String id, @RequestBody ProductInfo updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    // Endpoint to delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }


}
