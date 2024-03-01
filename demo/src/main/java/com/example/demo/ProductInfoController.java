package com.example.demo;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/api/products")
public class ProductInfoController {

    
    private final ProductInfoService productService;
    private final UserService userService;
    // private final JwTokenUtil jwTokenUtil;
    private String token;
  
    @Autowired
    public ProductInfoController(ProductInfoService productService,UserService userService) {
        this.productService = productService;
        this.userService = userService;
        // this.jwTokenUtil = jwTokenUtil;

    }

    @RequestMapping("/search/token")
    public ResponseEntity<String> receiveToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            this.token = authorizationHeader.substring(7); // Extract token after removing "Bearer "
        }
        System.out.println("i am in receive token function !");
        System.out.println(this.token);
        // Use the token for authentication/authorization or any other purpose
        return ResponseEntity.ok("Token received: " + token);
    }
    
    //@PostMapping
    // public ResponseEntity<?> saveProduct(@RequestBody ProductInfo product) {
    //     // ProductInfo savedProduct = ;
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     product.setUser((User)authentication);
    //     return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    //}
     
    // @RequestMapping("/api/products/{username}")
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductInfo product) {
  
        String id = getLoggedInUser();
        System.out.println(id);
        User user = userService.findById(id); // Replace userService with your user service
        if (user != null) {
            product.setUser(user);
            return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
        }
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
   }


    // Endpoint to get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductInfo> getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // private User getLoggedInUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication != null && authentication.isAuthenticated()) {
    //         String username = authentication.getName();
    //         return userService.findByUsername(username);
    //     }
    //     return null;
    // }

    private String getLoggedInUser() {
    //    try {
    //     System.out.println("I am in getloggedin user function !");
        
    //         Claims claims = Jwts.parser()
    //                 .setSigningKey(jwTokenUtil.getKey()) // Set your secret key used for signing the token
    //                 .parseClaimsJws(this.token)
    //                 .getBody();
    //           System.out.println(claims.get("id", String.class));
    //         // Extract the user ID from the claims
    //         return claims.get("id", String.class); // Assuming "id" is the key for user ID in token claims
    //     } catch (Exception e) {
    //         System.out.println("got error in getloggedin user !");
    //         // Token validation failed or token expired
    //         e.printStackTrace();
    //         return null;
    //     }
    return this.token;
    }

    // @GetMapping("/search/{searchTerm}")
    // public ResponseEntity<List<ProductInfo>> searchProductByName(@PathVariable String searchTerm) {
    //     List<ProductInfo> searchResults = productService.searchProducts(searchTerm);
    //     System.out.println("hii i am in search term function by name ");
    //     if (searchResults.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    //     return ResponseEntity.ok(searchResults);
    // }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<ProductInfo>> searchProductByName(@PathVariable String searchTerm) {
       
             String  id = getLoggedInUser();
   
        List<ProductInfo> searchResults = productService.searchProductsByUserAndName(id, searchTerm);
        if (searchResults.isEmpty()) {
            System.out.println("search by name not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchResults);
  
}


    // @GetMapping("/search/{searchTerm}/{sortOption}")
    // public ResponseEntity<List<ProductInfo>> searchProductByName(@PathVariable String searchTerm, @PathVariable String sortOption) {
    //     List<ProductInfo> searchResults = null;
    //     if ("name_asc".equals(sortOption)) {
    //         searchResults = productService.searchProductsSortedByName(searchTerm);
    //     } else if ("score_des".equals(sortOption)) {
    //         searchResults = productService.getProductsSortedByScore(searchTerm); // Provide the searchTerm here
    //     } 
    //     else if("score_asc".equals(sortOption))
    //     {
    //       //  System.out.println("sorting score in service method");
    //         searchResults = productService.searchAllProductsSortedByScoreAscSearchTerm(searchTerm);
       
    //     }
    //    else if("name_des".equals(sortOption))
    //     {
    //       //  System.out.println("hiiii");
    //         searchResults = productService.searchAllProductsSortedByNameDesSearchTerm(searchTerm);
       
    //     }else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid sort option
    //     }
    
    //     if (searchResults.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    //     return ResponseEntity.ok(searchResults);
    // }
    @GetMapping("/search/{searchTerm}/{sortOption}")
    public ResponseEntity<List<ProductInfo>> searchProductByName(@PathVariable String searchTerm, 
                                                                 @PathVariable String sortOption) {
    String id = getLoggedInUser();
   
            List<ProductInfo> searchResults = null;
             if("name_asc".equals(sortOption)) {

                searchResults = productService.searchProductsSortedByNameAndUserAsc(searchTerm, id);
            } else if ("score_des".equals(sortOption)) {

                searchResults = productService.searchProductsSortedByScoreAndUserDes(searchTerm, id);
            } else if ("score_asc".equals(sortOption)) {

                searchResults = productService.searchProductsSortedByScoreAndUserAsc(searchTerm, id);
            } else if ("name_des".equals(sortOption)) {

                searchResults = productService.searchProductsSortedByNameAndUserDes(searchTerm, id);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid sort option
            }
    
            if (searchResults.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(searchResults);
       
    }
    

    @GetMapping("/search/all/{sortOption}")
    public ResponseEntity<List<ProductInfo>> searchAllProductByName(
                                                                 @PathVariable String sortOption) {
        String id = getLoggedInUser();
      
            List<ProductInfo> searchResults = null;
            if ("name_asc".equals(sortOption)) {
                searchResults = productService.searchAllProductsSortedByNameAndUserAsc( id);
            } else if ("score_des".equals(sortOption)) {
                searchResults = productService.searchAllProductsSortedByScoreAndUserDes( id);
            } else if ("score_asc".equals(sortOption)) {
                searchResults = productService.searchAllProductsSortedByScoreAndUserAsc(id);
            } else if ("name_des".equals(sortOption)) {
                searchResults = productService.searchAllProductsSortedByNameAndUserDes(id);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid sort option
            }
    
            if (searchResults.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(searchResults);
        } 
    
    

    // @GetMapping("/search/all/{sortOption}")
    // public ResponseEntity<List<ProductInfo>> sortAllByName(@PathVariable String sortOption) {
    //     List<ProductInfo> searchResults= null;
    //     if("name_asc".equals(sortOption))
    //     {
    //         System.out.println("hiiii");
    //         searchResults = productService.searchAllProductsSortedByName();
       
    //     }
    //    else if("score_des".equals(sortOption))
    //     {
    //         System.out.println("sorting score in service method");
    //         searchResults = productService.searchAllProductsSortedByScore();
       
    //     }
    //     else if("score_asc".equals(sortOption))
    //     {
    //         System.out.println("sorting score in service method");
    //         searchResults = productService.searchAllProductsSortedByScoreAscending();
       
    //     }
    //    else if("name_des".equals(sortOption))
    //     {
    //         System.out.println("hiiii");
    //         searchResults = productService.searchAllProductsSortedByNameDescending();
       
    //     }
          
        
    
    //    else if (searchResults == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    //     return ResponseEntity.ok(searchResults);
    // }
    
 



@GetMapping("/search")
public ResponseEntity<List<ProductInfo>> searchProducts() {
String id = getLoggedInUser();
   
        List<ProductInfo> searchResults = productService.getProductsByUser(id);
        if (searchResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
