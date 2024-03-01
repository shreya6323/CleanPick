package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoRepository extends MongoRepository<ProductInfo, String> {

    ProductInfo findByProductName(String productName);
    List<ProductInfo> findAll(); 
    List<ProductInfo> findByUserIdAndProductNameContaining(String userId, String searchTerm);
    List<ProductInfo> findByProductNameContainingIgnoreCase(String name);
    List<ProductInfo> findByProductNameContainingIgnoreCaseOrderByProductName(String name);
    List<ProductInfo> findByUserId(String userId);
    List<ProductInfo> findByProductNameAndUserId(String productName, String userId);

    List<ProductInfo> findByOverallScoreAndUserId(String score, String userId);

   

    // Other methods if needed
}
