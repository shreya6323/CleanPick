package com.example.demo;
import java.util.Map;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Document(collection="cleanpick")
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Indexed(unique = true)
    
    private String productName;


 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

   ProductInfo()
   {

   }

   
   public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}
    public ProductInfo(String id, Map<String, Integer> ingredientScores, Double overallScore,String productName) {
        this.id = id;
        this.ingredientScores = ingredientScores;
        this.overallScore = overallScore;
        this.productName = productName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
  
    public Map<String, Integer> getIngredientScores() {
        return ingredientScores;
    }
    public void setIngredientScores(Map<String, Integer> ingredientScores) {
        this.ingredientScores = ingredientScores;
    }
    public Double getOverallScore() {
        return overallScore;
    }
    public void setOverallScore(Double overallScore) {
        this.overallScore = overallScore;
    }
    // Add fields for ingredient scores and overall score
    private Map<String, Integer> ingredientScores; // Map of ingredient name to score
    private Double overallScore;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    // Getters and setters
}
