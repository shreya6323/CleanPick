package com.example.demo;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductInfoService {
    private final ProductInfoRepository productInfoRepository;

    @Autowired
    public ProductInfoService(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }
    public List<ProductInfo> getAllProducts() {
        return productInfoRepository.findAll();
    }
    public ResponseEntity<?> saveProduct(ProductInfo product)  {  
         if (productInfoRepository.findByProductName(product.getProductName()) != null) {
            // Product with the same name already exists, return a conflict response
            System.out.println("product already exists !");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with the same name already exists");
        } else {
            // Product name is unique, save the product
            ProductInfo savedProduct = productInfoRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }
    }

  public ResponseEntity<ProductInfo> getProductById(String id) {
        ProductInfo product = productInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
        return ResponseEntity.ok().body(product);
    }

    public List<ProductInfo> searchProducts(String searchTerm) {
        // Search by product name or ingredient name
        return productInfoRepository.findByProductNameContainingIgnoreCase(searchTerm);
    }
    
   


    // Update Product
    public ResponseEntity<ProductInfo> updateProduct(String id, ProductInfo updatedProduct) {
        ProductInfo existingProduct = productInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
        
        existingProduct.setProductName(updatedProduct.getProductName());
        // Update other fields as needed
        
        ProductInfo savedProduct = productInfoRepository.save(existingProduct);
        return ResponseEntity.ok().body(savedProduct);
    }

 
    public List<ProductInfo> getProductsSortedByScore(String searchTerm) {
        List<ProductInfo> allProducts =  productInfoRepository.findByProductNameContainingIgnoreCase(searchTerm);
        return allProducts.stream()
                .sorted(Comparator.comparing(ProductInfo::getOverallScore).reversed())
                .collect(Collectors.toList());
    }

    public List<ProductInfo> searchProductsSortedByName(String searchTerm) {
       List<ProductInfo> productInfos = searchProducts(searchTerm);
      return productInfos.stream()
       .sorted(Comparator.comparing(ProductInfo::getProductName, String.CASE_INSENSITIVE_ORDER))
               .collect(Collectors.toList());
      
    }

    
    public List<ProductInfo> searchAllProductsSortedByNameDescending() {
        List<ProductInfo> productInfos = productInfoRepository.findAll();
       return productInfos.stream()
        .sorted(Comparator.comparing(ProductInfo::getProductName, String.CASE_INSENSITIVE_ORDER).reversed())
                .collect(Collectors.toList());
       
     }


    public List<ProductInfo> searchAllProductsSortedByScore() {
        System.out.println("sorting names all...");
        List<ProductInfo> products = productInfoRepository.findAll(); // Fetch all products
        // Sort the list by name
        return products.stream()
                .sorted(Comparator.comparing(ProductInfo::getOverallScore).reversed())
                .collect(Collectors.toList());
       
    }

    public List<ProductInfo> searchAllProductsSortedByScoreAscending() {
        System.out.println("sorting names all...");
        List<ProductInfo> products = productInfoRepository.findAll(); // Fetch all products
        // Sort the list by name
        return products.stream()
                .sorted(Comparator.comparing(ProductInfo::getOverallScore))
                .collect(Collectors.toList());
       
    }


    
    public List<ProductInfo> searchAllProductsSortedByName() {
        System.out.println("sorting scores all...");
        List<ProductInfo> products = productInfoRepository.findAll(); // Fetch all products
        // Sort the list by name
        List<ProductInfo> sortedProducts = products.stream()
        .sorted(Comparator.comparing(ProductInfo::getProductName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        return sortedProducts;
    }

    // Delete Product
    public ResponseEntity<Void> deleteProduct(String id) {
        ProductInfo existingProduct = productInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id " + id));
        
        productInfoRepository.delete(existingProduct);
        return ResponseEntity.noContent().build();
    }
    public List<ProductInfo> searchAllProductsSortedByScoreAscSearchTerm(String searchTerm) {
        // TODO Auto-generated method stub
        System.out.println("sorting scores ascending based on search term ...");
        List<ProductInfo> products = searchProducts(searchTerm); // Fetch all products
        // Sort the list by name
        return products.stream()
                .sorted(Comparator.comparing(ProductInfo::getOverallScore))
                .collect(Collectors.toList());
    }
    public List<ProductInfo> searchAllProductsSortedByNameDesSearchTerm(String searchTerm) {
        System.out.println("sorting names descending based on search term ...");
        List<ProductInfo> productInfos = searchProducts(searchTerm);
        return productInfos.stream()
         .sorted(Comparator.comparing(ProductInfo::getProductName, String.CASE_INSENSITIVE_ORDER).reversed())
                 .collect(Collectors.toList());
    }
  

   

    

  


}
    // Add other methods as needed for querying product data

